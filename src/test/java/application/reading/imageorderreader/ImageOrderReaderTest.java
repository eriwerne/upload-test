package application.reading.imageorderreader;

import application.reading.exception.ArticleNotFound;
import application.reading.exception.OrderNotFound;
import application.reading.exception.ResourceFailure;
import core.image.DetailFotografie;
import core.image.ImageOrder;
import injection.ImageOrderReaderSourceMock;
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
    private HashMap<String, List<ImageOrder>> responseArticleImageOrderMap;
    private String requestArticleNumber;
    private String orderNumber;

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
    public void when_imageorderreader_is_called_for_one_article_then_it_retunrs_one_imageorder() throws OrderNotFound, ResourceFailure, ArticleNotFound {
        appendArticleImageOrderToResponse(requestArticleNumber, fixtureImageOrder());
        mockImageOrderSourceForArticleList();

        cut.readImageOrdersForOrderNumber(orderNumber);
        List<ImageOrder> act = cut.returnImageOrdersForArticle(requestArticleNumber);
        assertNotNull(act);
        assertEquals(1, act.size());
    }

    @Test
    public void when_imageorderreader_is_called_for_two_Articles_were_one_has_a_subset_of_functions_of_the_other_then_filenames_are_not_merged() throws OrderNotFound, ResourceFailure, ArticleNotFound {
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
    public void when_imageorderreader_is_called_and_persisted_result_not_exists_then_mmp_is_requested() throws ResourceFailure, OrderNotFound {
        cut.readImageOrdersForOrderNumber(orderNumber);
        verify(mockImageOrderReaderSource, times(1)).readImageOrdersForOrderNumber(orderNumber);
    }
}
