package application.reading.imageorderreader;

import application.output.Persister;
import application.output.PersisterFailure;
import application.reading.exception.ArticleNotFound;
import application.reading.exception.OrderNotFound;
import application.reading.exception.ResourceFailure;
import core.image.DetailFotografie;
import core.image.ImageOrder;
import injection.ImageOrderReaderSourceMock;
import injection.PersisterMock;
import org.junit.Before;
import org.junit.Test;
import utils.UnitTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class ImageOrderReaderTest extends UnitTest {
    private ImageOrderReader cut;
    private ImageOrderReaderSource mockImageOrderReaderSource;
    private Persister mockPersister;
    private HashMap<String, List<ImageOrder>> responseArticleImageOrderMap;
    private String requestArticleNumber;
    private String orderNumber;
    private final String downloadFolder = "cache/imageorders/";

    private void mockImageOrderSourceForArticleList() throws OrderNotFound, ResourceFailure {
        when(mockImageOrderReaderSource.readImageOrdersForOrderNumber(orderNumber)).thenReturn(responseArticleImageOrderMap);
    }

    private void appendArticleImageOrderToResponse(String articleNumber, ImageOrder imageOrder) {
        List<ImageOrder> imageOrders;
        if (responseArticleImageOrderMap.containsKey(articleNumber)) {
            imageOrders = responseArticleImageOrderMap.get(articleNumber);
        } else {
            imageOrders = new ArrayList<>();
            responseArticleImageOrderMap.put(articleNumber, imageOrders);
        }
        imageOrders.add(imageOrder);
    }

    @Before
    public void setup() {
        orderNumber = fixtureString();
        mockPersister = new PersisterMock().getPersisterMock();
        mockImageOrderReaderSource = new ImageOrderReaderSourceMock().getImageOrderReaderSourceMock();

        responseArticleImageOrderMap = new HashMap<>();
        requestArticleNumber = fixtureString();
        cut = new ImageOrderReader();
    }

    @Test(expected = ArticleNotFound.class)
    public void when_imageorderreader_is_called_for_unknown_article_then_raise_exception() throws ArticleNotFound {
        cut.returnImageOrdersForArticle(fixtureString());
    }

    @Test
    public void when_imageorderreader_is_called_for_one_article_then_it_retunrs_one_imageorder() throws OrderNotFound, ResourceFailure, ArticleNotFound, PersisterFailure {
        appendArticleImageOrderToResponse(requestArticleNumber, fixtureImageOrder());
        mockImageOrderSourceForArticleList();

        cut.readImageOrdersForOrderNumber(orderNumber);
        List<ImageOrder> act = cut.returnImageOrdersForArticle(requestArticleNumber);
        assertNotNull(act);
        assertEquals(1, act.size());
    }

    @Test
    public void when_imageorderreader_is_called_for_two_Articles_were_one_has_a_subset_of_functions_of_the_other_then_filenames_are_not_merged() throws OrderNotFound, ResourceFailure, ArticleNotFound, PersisterFailure {
        String articleNumberA = fixtureString();
        String articleNumberAB = fixtureString();

        String perspective = fixtureString();
        String filenameA = fixtureString();
        String filenameAB = fixtureString();
        String inhaltsklassifizierung = fixtureString();
        String aufnahmeart = fixtureString();
        ImageOrder imageOrderA = new ImageOrder(perspective, listOfFilename(filenameA), inhaltsklassifizierung, aufnahmeart, DetailFotografie.FUNKTION);
        ImageOrder imageOrderAB = new ImageOrder(perspective, listOfFilename(filenameAB), inhaltsklassifizierung, aufnahmeart, DetailFotografie.FUNKTION);

        appendArticleImageOrderToResponse(articleNumberA, imageOrderA);
        appendArticleImageOrderToResponse(articleNumberAB, imageOrderAB);

        mockImageOrderSourceForArticleList();

        cut.readImageOrdersForOrderNumber(orderNumber);
        List<ImageOrder> act1 = cut.returnImageOrdersForArticle(articleNumberA);
        List<ImageOrder> act2 = cut.returnImageOrdersForArticle(articleNumberAB);

        assertTrue(act1.get(0).getFilenames().contains(filenameA));
        assertFalse(act1.get(0).getFilenames().contains(filenameAB));
        assertFalse(act2.get(0).getFilenames().contains(filenameA));
        assertTrue(act2.get(0).getFilenames().contains(filenameAB));
    }

    @Test
    public void when_imageorderreader_is_called_then_persisted_result_is_checked() throws ResourceFailure, OrderNotFound, PersisterFailure {
        cut.readImageOrdersForOrderNumber(orderNumber);
        verify(mockPersister, times(1)).pathExists(eq(downloadFolder), eq(orderNumber));
    }

    @Test
    public void when_imageorderreader_is_called_and_persisted_result_not_exists_then_mmp_is_requested() throws ResourceFailure, OrderNotFound, PersisterFailure {
        cut.readImageOrdersForOrderNumber(orderNumber);
        verify(mockImageOrderReaderSource, times(1)).readImageOrdersForOrderNumber(orderNumber);
    }

    @Test
    public void when_imageorderreader_is_called_and_persisted_result_not_exists_then_result_is_persisted() throws ResourceFailure, OrderNotFound, PersisterFailure {
        cut.readImageOrdersForOrderNumber(orderNumber);
        verify(mockPersister, times(1)).persistObject(eq(downloadFolder), eq(orderNumber), any());
    }

    @Test
    public void when_imageorderreader_is_called_and_persisted_result_not_exists_then_peristed_result_is_not_loaded() throws ResourceFailure, OrderNotFound, PersisterFailure {
        cut.readImageOrdersForOrderNumber(orderNumber);
        verify(mockPersister, times(0)).getObject(eq(downloadFolder), eq(orderNumber));
    }

    @Test
    public void when_imageorderreader_is_called_and_persisted_result_exists_then_mmp_is_not_requested() throws ResourceFailure, OrderNotFound, PersisterFailure {
        when(mockPersister.pathExists(downloadFolder, orderNumber)).thenReturn(true);
        cut.readImageOrdersForOrderNumber(orderNumber);
        verify(mockImageOrderReaderSource, times(0)).readImageOrdersForOrderNumber(orderNumber);
    }

    @Test
    public void when_imageorderreader_is_called_and_persisted_result_exists_then_result_is_not_persisted() throws ResourceFailure, OrderNotFound, PersisterFailure {
        when(mockPersister.pathExists(downloadFolder, orderNumber)).thenReturn(true);
        cut.readImageOrdersForOrderNumber(orderNumber);
        verify(mockPersister, times(0)).persistObject(eq(downloadFolder), eq(orderNumber), any());
    }

    @Test
    public void when_imageorderreader_is_called_and_persisted_result_exists_then_peristed_result_is_loaded() throws ResourceFailure, OrderNotFound, PersisterFailure {
        when(mockPersister.pathExists(downloadFolder, orderNumber)).thenReturn(true);
        cut.readImageOrdersForOrderNumber(orderNumber);
        verify(mockPersister, times(1)).getObject(eq(downloadFolder), eq(orderNumber));
    }
}
