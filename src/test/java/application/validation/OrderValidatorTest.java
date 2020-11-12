package application.validation;

import application.reading.articlereader.ArticleReader;
import application.reading.articlereader.ArticleReaderSource;
import application.reading.exception.ArticleNotFound;
import application.reading.exception.OrderNotFound;
import application.reading.exception.ResourceFailure;
import application.reading.imageorderreader.ImageOrderReader;
import application.reading.imageorderreader.ImageOrderReaderSource;
import application.reading.orderreader.OrderReader;
import application.reading.orderreader.OrderReaderSource;
import application.validation.exceptions.CouldNotPerformValidationException;
import application.validation.exceptions.DuplicateContainerIdsException;
import application.validation.exceptions.MissingContainerIdsException;
import application.validation.exceptions.TooManyContainerIdsException;
import core.article.Article;
import core.article.exceptions.InvalidArticleData;
import core.image.ImageOrder;
import injection.ArticleReaderSourceMock;
import injection.ImageOrderReaderSourceMock;
import injection.OrderReaderSourceMock;
import injection.PersisterMock;
import org.junit.Before;
import org.junit.Test;
import utils.ResourceFileReader;
import utils.UnitTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.when;

public class OrderValidatorTest extends UnitTest {
    private OrderValidator cut;
    private OrderReaderSource orderReaderSourceMock;
    private ArticleReaderSource articleReaderSourceMock;
    private ImageOrderReaderSource imageOrderReaderSourceMock;

    private String getJsonFromFile(String filename) {
        try {
            return ResourceFileReader.readResource("orders/validation/" + filename + ".json");
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Before
    public void setup() {
        new PersisterMock();
        orderReaderSourceMock = new OrderReaderSourceMock().getOrderReaderSourceMock();
        articleReaderSourceMock = new ArticleReaderSourceMock().getArticleReaderSourceMock();
        imageOrderReaderSourceMock = new ImageOrderReaderSourceMock().getImageOrderReaderSourceMock();
        cut = new OrderValidator(
                new OrderReader(),
                new ArticleReader(),
                new ImageOrderReader()
        );
    }

    @Test
    public void when_validation_is_executed_for_valid_order_then_validation_throws_no_exception() throws MissingContainerIdsException, TooManyContainerIdsException, DuplicateContainerIdsException, ResourceFailure, OrderNotFound, ArticleNotFound, CouldNotPerformValidationException, InvalidArticleData {
        String articleNumber = fixtureString();

        ArrayList<String> articleNumbers = new ArrayList<>();
        articleNumbers.add(articleNumber);

        List<Article> articles = new ArrayList<>();
        articles.add(createArticleForArticleNumber(articleNumber));

        HashMap<String, List<ImageOrder>> imageOrders = new HashMap<>();
        List<ImageOrder> imageOrders1 = new ArrayList<>();
        imageOrders1.add(new ImageOrder(fixtureString(), listOfFilename("1231"), fixtureString(), fixtureString(), null));
        imageOrders1.add(new ImageOrder(fixtureString(), listOfFilename("1232"), fixtureString(), fixtureString(), null));
        imageOrders1.add(new ImageOrder(fixtureString(), listOfFilename("3451"), fixtureString(), fixtureString(), null));
        imageOrders1.add(new ImageOrder(fixtureString(), listOfFilename("3452"), fixtureString(), fixtureString(), null));
        imageOrders.put(articleNumber, imageOrders1);

        String orderNumber = "12345";
        when(orderReaderSourceMock.readArticleNumbersInOrder(orderNumber)).thenReturn(articleNumbers);
        when(articleReaderSourceMock.readArticles(articleNumbers)).thenReturn(articles);
        when(imageOrderReaderSourceMock.readImageOrdersForOrderNumber(orderNumber)).thenReturn(imageOrders);
        ContainerIdExtractorForJSON containerIdExtractor = new ContainerIdExtractorForJSON(getJsonFromFile("valid_order"));
        cut.validateContainerIdsForOrderNumber(orderNumber, containerIdExtractor.readContainerIdsInObject());
    }

    @Test(expected = MissingContainerIdsException.class)
    public void when_validation_is_executed_for_order_with_missing_container_ids_then_validation_throws_exception() throws MissingContainerIdsException, TooManyContainerIdsException, DuplicateContainerIdsException, ResourceFailure, ArticleNotFound, InvalidArticleData, OrderNotFound, CouldNotPerformValidationException {
        String articleNumber = fixtureString();

        ArrayList<String> articleNumbers = new ArrayList<>();
        articleNumbers.add(articleNumber);

        List<Article> articles = new ArrayList<>();
        articles.add(createArticleForArticleNumber(articleNumber));

        HashMap<String, List<ImageOrder>> imageOrders = new HashMap<>();
        List<ImageOrder> imageOrders1 = new ArrayList<>();
        imageOrders1.add(new ImageOrder(fixtureString(), listOfFilename("1231"), fixtureString(), fixtureString(), null));
        imageOrders1.add(new ImageOrder(fixtureString(), listOfFilename("1232"), fixtureString(), fixtureString(), null));
        imageOrders1.add(new ImageOrder(fixtureString(), listOfFilename("3451"), fixtureString(), fixtureString(), null));
        imageOrders1.add(new ImageOrder(fixtureString(), listOfFilename("3452"), fixtureString(), fixtureString(), null));
        imageOrders.put(articleNumber, imageOrders1);

        String orderNumber = "12345";
        when(orderReaderSourceMock.readArticleNumbersInOrder(orderNumber)).thenReturn(articleNumbers);
        when(articleReaderSourceMock.readArticles(articleNumbers)).thenReturn(articles);
        when(imageOrderReaderSourceMock.readImageOrdersForOrderNumber(orderNumber)).thenReturn(imageOrders);
        final ContainerIdExtractorForJSON containerIdExtractor = new ContainerIdExtractorForJSON(getJsonFromFile("missing_container_ids"));
        cut.validateContainerIdsForOrderNumber(orderNumber, containerIdExtractor.readContainerIdsInObject());
    }

    @Test(expected = TooManyContainerIdsException.class)
    public void when_validation_is_executed_for_order_with_too_many_container_ids_then_validation_throws_exception() throws MissingContainerIdsException, TooManyContainerIdsException, DuplicateContainerIdsException, ArticleNotFound, CouldNotPerformValidationException, ResourceFailure, OrderNotFound, InvalidArticleData {
        String articleNumber = fixtureString();

        ArrayList<String> articleNumbers = new ArrayList<>();
        articleNumbers.add(articleNumber);

        List<Article> articles = new ArrayList<>();
        articles.add(createArticleForArticleNumber(articleNumber));

        HashMap<String, List<ImageOrder>> imageOrders = new HashMap<>();
        List<ImageOrder> imageOrders1 = new ArrayList<>();
        imageOrders1.add(new ImageOrder(fixtureString(), listOfFilename("1231"), fixtureString(), fixtureString(), null));
        imageOrders1.add(new ImageOrder(fixtureString(), listOfFilename("1232"), fixtureString(), fixtureString(), null));
        imageOrders1.add(new ImageOrder(fixtureString(), listOfFilename("3451"), fixtureString(), fixtureString(), null));
        imageOrders1.add(new ImageOrder(fixtureString(), listOfFilename("3452"), fixtureString(), fixtureString(), null));
        imageOrders.put(articleNumber, imageOrders1);

        String orderNumber = "12345";
        when(orderReaderSourceMock.readArticleNumbersInOrder(orderNumber)).thenReturn(articleNumbers);
        when(articleReaderSourceMock.readArticles(articleNumbers)).thenReturn(articles);
        when(imageOrderReaderSourceMock.readImageOrdersForOrderNumber(orderNumber)).thenReturn(imageOrders);
        final ContainerIdExtractorForJSON containerIdExtractor = new ContainerIdExtractorForJSON(getJsonFromFile("too_many_container_ids"));
        cut.validateContainerIdsForOrderNumber(orderNumber, containerIdExtractor.readContainerIdsInObject());
    }

    @Test(expected = DuplicateContainerIdsException.class)
    public void when_validation_is_executed_for_order_with_too_double_container_ids_then_validation_throws_exception() throws MissingContainerIdsException, TooManyContainerIdsException, DuplicateContainerIdsException, ResourceFailure, OrderNotFound, ArticleNotFound, InvalidArticleData, CouldNotPerformValidationException {
        String articleNumber = fixtureString();

        ArrayList<String> articleNumbers = new ArrayList<>();
        articleNumbers.add(articleNumber);

        List<Article> articles = new ArrayList<>();
        articles.add(createArticleForArticleNumber(articleNumber));

        HashMap<String, List<ImageOrder>> imageOrders = new HashMap<>();
        List<ImageOrder> imageOrders1 = new ArrayList<>();
        imageOrders1.add(new ImageOrder(fixtureString(), listOfFilename("1231"), fixtureString(), fixtureString(), null));
        imageOrders1.add(new ImageOrder(fixtureString(), listOfFilename("1232"), fixtureString(), fixtureString(), null));
        imageOrders1.add(new ImageOrder(fixtureString(), listOfFilename("3451"), fixtureString(), fixtureString(), null));
        imageOrders1.add(new ImageOrder(fixtureString(), listOfFilename("3452"), fixtureString(), fixtureString(), null));
        imageOrders.put(articleNumber, imageOrders1);

        String orderNumber = "12345";
        when(orderReaderSourceMock.readArticleNumbersInOrder(orderNumber)).thenReturn(articleNumbers);
        when(articleReaderSourceMock.readArticles(articleNumbers)).thenReturn(articles);
        when(imageOrderReaderSourceMock.readImageOrdersForOrderNumber(orderNumber)).thenReturn(imageOrders);
        final ContainerIdExtractorForJSON containerIdExtractor = new ContainerIdExtractorForJSON(getJsonFromFile("double_container_ids"));
        cut.validateContainerIdsForOrderNumber(orderNumber, containerIdExtractor.readContainerIdsInObject());
    }
}
