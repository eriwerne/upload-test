package application;

import application.dataprocessing.filtering.FilterForNonFunctionArticles;
import application.dataprocessing.matching.ArticleDuplicateMatcher;
import application.dataprocessing.matching.ArticleMirrorMatcher;
import application.dataprocessing.matching.MirrorRelations;
import application.dataprocessing.merging.ImageOrderDuplicateMerger;
import application.dataprocessing.merging.ImageOrderMirrorMerger;
import application.dataprocessing.packing.OrderPacker;
import application.exceptions.ApplicationFailed;
import application.output.OutputFailed;
import application.output.OutputGenerator;
import application.output.PersisterFailure;
import application.output.formats.OutputFormatType;
import application.reading.articlereader.ArticleReader;
import application.reading.exception.ArticleNotFound;
import application.reading.exception.OrderNotFound;
import application.reading.exception.ResourceFailure;
import application.reading.imageorderreader.ImageOrderReader;
import application.reading.orderreader.OrderReader;
import application.validation.*;
import application.validation.exceptions.ContainerIdValidationException;
import application.validation.exceptions.CouldNotPerformValidationException;
import core.ArticleImageOrderCollection;
import core.article.Article;
import core.article.exceptions.InvalidArticleData;
import core.image.ImageOrder;
import core.order.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static application.output.formats.OutputFormatType.CSV;
import static application.output.formats.OutputFormatType.JSON;

public class ProcessMediator {
    private final OrderReader orderReader;
    private final ArticleReader articleReader;
    private final ImageOrderReader imageOrderReader;
    private HashMap<OutputFormatType, String> outputStrings;

    public ProcessMediator() {
        articleReader = new ArticleReader();
        imageOrderReader = new ImageOrderReader();
        orderReader = new OrderReader();
    }

    public void runOrderNumber(String orderNumber, String filename, String foldername) throws ApplicationFailed {
        try {
            ArticleImageOrderCollection articleImageOrders = readArticleAndImageOrdersForOrder(orderNumber);
            mergeImageOrders(articleImageOrders);
            filterUnnecessaryImageOrders(articleImageOrders);
            Order order = createOrder(articleImageOrders, orderNumber);
            outputStrings = generateOutputFiles(order, filename, foldername);
            validate(order, outputStrings);

        } catch (ContainerIdValidationException | OutputFailed | InvalidArticleData | OrderNotFound | ResourceFailure | ArticleNotFound | PersisterFailure e) {
            throw new ApplicationFailed(e);
        }
    }

    private ArticleImageOrderCollection readArticleAndImageOrdersForOrder(String orderNumber) throws InvalidArticleData, OrderNotFound, ResourceFailure, ArticleNotFound, PersisterFailure {
        System.out.println("* Reading");
        List<String> articleNumbers = orderReader.readArticleNumbersInOrder(orderNumber);
        HashMap<String, Article> articles = articleReader.readArticles(articleNumbers);
        System.out.println();
        return readImageOrdersForOrderNumber(orderNumber, articles);
    }

    private void mergeImageOrders(ArticleImageOrderCollection articleImageOrders) {
        ArticleMirrorMatcher articleMirrorMatcher = new ArticleMirrorMatcher();
        ImageOrderMirrorMerger imageOrderMirrorMerger = new ImageOrderMirrorMerger();
        ArticleDuplicateMatcher articleDuplicateMatcher = new ArticleDuplicateMatcher();
        ImageOrderDuplicateMerger imageOrderDuplicateMerger = new ImageOrderDuplicateMerger();

        System.out.println("* Merging");
        MirrorRelations mirrorRelations = articleMirrorMatcher.getMirrorRelations(articleImageOrders.getArticlesAsList());
        imageOrderMirrorMerger.moveFilenamesOfMirrorArticles(articleImageOrders);
        imageOrderMirrorMerger.mergeFilenamesToMirrorImageOrders(mirrorRelations, articleImageOrders);
        List<List<String>> duplicatesGroups = articleDuplicateMatcher.matchDuplicatesGroups(articleImageOrders.getArticlesAsList());
        imageOrderDuplicateMerger.mergeFilenamesToDuplicateImageOrders(duplicatesGroups, articleImageOrders);
    }

    private void validate(Order order, HashMap<OutputFormatType, String> output) throws ContainerIdValidationException, ArticleNotFound {
        System.out.println("* Validating container ids in output files");
        List<ContainerIdExtractor> containerIdExtractorList = new ArrayList<>();
        containerIdExtractorList.add(new ContainerIdExtractorForOrderObject(order));
        containerIdExtractorList.add(new ContainerIdExtractorForJSON(output.get(JSON)));
        containerIdExtractorList.add(new ContainerIdExtractorForCSV(output.get(CSV)));

        for (ContainerIdExtractor extractor : containerIdExtractorList) {
            try {
                OrderValidator orderValidator = new OrderValidator(orderReader, articleReader, imageOrderReader);
                orderValidator.validateContainerIdsForOrderNumber(order.getOrderNumber(), extractor.readContainerIdsInObject());
            } catch (CouldNotPerformValidationException e) {
                e.printStackTrace();
            }
        }
    }

    private HashMap<OutputFormatType, String> generateOutputFiles(Order order, String filename, String foldername) throws OutputFailed {
        OutputGenerator outputGenerator = new OutputGenerator();

        System.out.println("* Writing");
        outputGenerator.run(order, filename, foldername);
        return outputGenerator.getOutputStrings();
    }

    private void filterUnnecessaryImageOrders(ArticleImageOrderCollection articleImageOrders) {
        FilterForNonFunctionArticles filterForNonFunctionArticles = new FilterForNonFunctionArticles();
        System.out.println("* Filtering");
        filterForNonFunctionArticles.filter(articleImageOrders);
    }

    private ArticleImageOrderCollection readImageOrdersForOrderNumber(String orderNumber, HashMap<String, Article> articles) throws OrderNotFound, ResourceFailure, ArticleNotFound, PersisterFailure {
        imageOrderReader.readImageOrdersForOrderNumber(orderNumber);
        ArticleImageOrderCollection articleImageOrderMap = new ArticleImageOrderCollection();
        for (Article article : articles.values()) {
            articleImageOrderMap.addArticle(article);
            List<ImageOrder> imageOrders = imageOrderReader.returnImageOrdersForArticle(article.getArticleNumber());
            for (ImageOrder imageOrder : imageOrders)
                articleImageOrderMap.addImageOrderToArticle(imageOrder, article);
        }
        return articleImageOrderMap;
    }

    private Order createOrder(ArticleImageOrderCollection articleImageOrders, String orderNumber) {
        System.out.println("* Packing");
        OrderPacker orderPacker = new OrderPacker();
        return orderPacker.createOrder(orderNumber, articleImageOrders);
    }

    public HashMap<OutputFormatType, String> getOutputStrings() {
        return outputStrings;
    }
}
