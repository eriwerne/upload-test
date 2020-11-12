package application.validation;

import application.reading.articlereader.ArticleReader;
import application.reading.exception.ArticleNotFound;
import application.reading.exception.OrderNotFound;
import application.reading.exception.ResourceFailure;
import application.reading.imageorderreader.ImageOrderReader;
import application.reading.orderreader.OrderReader;
import application.validation.exceptions.CouldNotPerformValidationException;
import application.validation.exceptions.DuplicateContainerIdsException;
import application.validation.exceptions.MissingContainerIdsException;
import application.validation.exceptions.TooManyContainerIdsException;
import core.article.Article;
import core.article.exceptions.InvalidArticleData;
import core.image.ImageOrder;

import java.util.*;

public class OrderValidator {

    private final ImageOrderReader imageOrderReader;
    private final OrderReader orderReader;
    private final ArticleReader articleReader;

    public OrderValidator(OrderReader orderReader, ArticleReader articleReader, ImageOrderReader imageOrderReader) {
        this.imageOrderReader = imageOrderReader;
        this.orderReader = orderReader;
        this.articleReader = articleReader;
    }

    public void validateContainerIdsForOrderNumber(String orderNumber, List<String> actualContainerIds) throws MissingContainerIdsException, TooManyContainerIdsException, DuplicateContainerIdsException, CouldNotPerformValidationException, ArticleNotFound {
        try {
            HashMap<String, List<ImageOrder>> expectedImageOrders = readExpectedImageOrders(orderNumber);
            checkForMissingContainerIds(actualContainerIds, expectedImageOrders);
            checkForTooManyContainerIds(actualContainerIds, expectedImageOrders);
            checkForDoubleContainerIds(actualContainerIds);
        } catch (ResourceFailure | OrderNotFound | InvalidArticleData e) {
            throw new CouldNotPerformValidationException(e);
        }
    }

    private void checkForDoubleContainerIds(List<String> actualContainerIds) throws DuplicateContainerIdsException {
        HashSet<String> uniqueContainerIds = new HashSet<>();
        ArrayList<String> doubleContainerIds = new ArrayList<>();
        actualContainerIds.forEach(containerId -> {
            if (uniqueContainerIds.contains(containerId))
                doubleContainerIds.add(containerId);
            else
                uniqueContainerIds.add(containerId);
        });
        if (!doubleContainerIds.isEmpty())
            throw new DuplicateContainerIdsException(doubleContainerIds);
    }

    private void checkForTooManyContainerIds(List<String> actualContainerIds, HashMap<String, List<ImageOrder>> expectedImageOrders) throws TooManyContainerIdsException {
        ArrayList<String> tooManyContainerIds = new ArrayList<>();
        ArrayList<String> expectedContainerIds = new ArrayList<>();
        expectedImageOrders.values().forEach(expectedImageOrdersForArticle -> {
            expectedImageOrdersForArticle.forEach(expectedImageOrder -> {
                expectedContainerIds.addAll(expectedImageOrder.getFilenames());
                expectedContainerIds.addAll(expectedImageOrder.getFilenamesMirror());
            });
        });
        actualContainerIds.forEach(actualContainerId -> {
            if (!expectedContainerIds.contains(actualContainerId))
                tooManyContainerIds.add(actualContainerId);
        });
        if (!tooManyContainerIds.isEmpty())
            throw new TooManyContainerIdsException(tooManyContainerIds);
    }

    private HashMap<String, List<ImageOrder>> readExpectedImageOrders(String orderNumber) throws ResourceFailure, OrderNotFound, ArticleNotFound {
        HashMap<String, List<ImageOrder>> expectedContainerIds = new HashMap<>();
        List<String> articleNumbers = orderReader.readArticleNumbersInOrder(orderNumber);
        imageOrderReader.readImageOrdersForOrderNumber(orderNumber);
        for (String articleNumber : articleNumbers)
            expectedContainerIds.put(articleNumber, imageOrderReader.returnImageOrdersForArticle(articleNumber));
        return expectedContainerIds;
    }

    private void checkForMissingContainerIds(List<String> actualContainerIds, HashMap<String, List<ImageOrder>> expectedImageOrders) throws MissingContainerIdsException, ResourceFailure, InvalidArticleData, ArticleNotFound {
        List<String> missingContainerIds = new ArrayList<>();
        for (String articleNumber : expectedImageOrders.keySet())
            for (ImageOrder expectationImageOrder : expectedImageOrders.get(articleNumber))
                for (String expectationContainerId : expectationImageOrder.getFilenames())
                    if (!actualContainerIds.contains(expectationContainerId)) {
                        if (isImageOrderToIgnore(articleNumber, expectationImageOrder))
                            continue;
                        missingContainerIds.add(expectationContainerId);
                    }
        if (!missingContainerIds.isEmpty())
            throw new MissingContainerIdsException(missingContainerIds);
    }

    private boolean isImageOrderToIgnore(String articleNumber, ImageOrder imageOrder) throws ResourceFailure, InvalidArticleData, ArticleNotFound {
        HashMap<String, Article> articles = articleReader.readArticles(Collections.singletonList(articleNumber));
        Article article = articles.get(articleNumber);
        Boolean isFunctionImage = imageOrder.isFunctionImage();
        boolean isFunctionArticle = article.hasFunction();
        return isFunctionImage && !isFunctionArticle;
    }
}
