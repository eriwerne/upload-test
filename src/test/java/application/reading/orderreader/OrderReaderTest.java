package application.reading.orderreader;

import application.output.Persister;
import application.output.PersisterFailure;
import application.reading.exception.OrderNotFound;
import application.reading.exception.ResourceFailure;
import injection.OrderReaderSourceMock;
import injection.PersisterMock;
import org.junit.Before;
import org.junit.Test;
import utils.UnitTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class OrderReaderTest extends UnitTest {
    private OrderReader cut;
    private Persister mockPersister;
    private OrderReaderSource mockOrderReaderSource;
    private String orderNumber;
    private final String downloadFolder = "cache/order/";
    private final String articleNumber = "123456";

    @Before
    public void setup() throws ResourceFailure, OrderNotFound {
        mockOrderReaderSource = new OrderReaderSourceMock().getOrderReaderSourceMock();
        mockPersister = new PersisterMock().getPersisterMock();
        cut = new OrderReader();
        orderNumber = fixtureString();
        ArrayList<String> articleNumbers = new ArrayList<>();
        articleNumbers.add(articleNumber);
        when(mockOrderReaderSource.readArticleNumbersInOrder(orderNumber)).thenReturn(articleNumbers);
    }

    @Test
    public void when_order_reader_is_executed_and_persisted_result_not_exists_then_reader_source_is_requesetd() throws ResourceFailure, OrderNotFound, PersisterFailure {
        cut.readArticleNumbersInOrder(orderNumber);
        verify(mockOrderReaderSource, times(1)).readArticleNumbersInOrder(orderNumber);
    }

    @Test
    public void when_order_reader_is_executed_and_persisted_result_not_exists_then_result_is_returned() throws ResourceFailure, OrderNotFound, PersisterFailure {
        List<String> act = cut.readArticleNumbersInOrder(orderNumber);
        assertTrue(act.contains(articleNumber));
    }

    @Test
    public void when_order_reader_is_executed_and_persisted_result_not_exists_then_persisted_result_is_checked() throws ResourceFailure, OrderNotFound, PersisterFailure {
        cut.readArticleNumbersInOrder(orderNumber);
        verify(mockPersister, times(1)).pathExists(downloadFolder, orderNumber);
    }

    @Test
    public void when_order_reader_is_executed_and_persisted_result_not_exists_then_result_is_persisted() throws ResourceFailure, OrderNotFound, PersisterFailure {
        cut.readArticleNumbersInOrder(orderNumber);
        verify(mockPersister, times(1)).persistObject(eq(downloadFolder), eq(orderNumber), any());
    }

    @Test
    public void when_order_reader_is_executed_and_persisted_result_not_exists_then_result_is_not_loaded() throws ResourceFailure, OrderNotFound, PersisterFailure {
        cut.readArticleNumbersInOrder(orderNumber);
        verify(mockPersister, times(0)).getObject(downloadFolder, orderNumber);
    }

    @Test
    public void when_order_reader_is_executed_and_result_esists_then_result_is_not_persisted() throws ResourceFailure, OrderNotFound, PersisterFailure {
        when(mockPersister.pathExists(downloadFolder, orderNumber)).thenReturn(true);
        cut.readArticleNumbersInOrder(orderNumber);
        verify(mockPersister, times(0)).persistObject(eq(downloadFolder), eq(orderNumber), any());
    }

    @Test
    public void when_order_reader_is_executed_and_result_esists_then_reader_source_is_not_requesetd() throws ResourceFailure, OrderNotFound, PersisterFailure {
        when(mockPersister.pathExists(downloadFolder, orderNumber)).thenReturn(true);
        cut.readArticleNumbersInOrder(orderNumber);
        verify(mockOrderReaderSource, times(0)).readArticleNumbersInOrder(orderNumber);
    }

    @Test
    public void when_order_reader_is_executed_and_result_esists_then_result_is_loaded() throws ResourceFailure, OrderNotFound, PersisterFailure {
        when(mockPersister.pathExists(downloadFolder, orderNumber)).thenReturn(true);
        cut.readArticleNumbersInOrder(orderNumber);
        verify(mockPersister, times(1)).getObject(downloadFolder, orderNumber);
    }

    @Test
    public void when_order_reader_is_executed_and_result_esists_then_result_is_returned() throws ResourceFailure, OrderNotFound, PersisterFailure {
        when(mockPersister.pathExists(downloadFolder, orderNumber)).thenReturn(true);
        ArrayList<Object> articleNumbers = new ArrayList<>();
        articleNumbers.add(articleNumber);
        when(mockPersister.getObject(downloadFolder, orderNumber)).thenReturn(articleNumbers);
        List<String> act = cut.readArticleNumbersInOrder(orderNumber);
        assertTrue(act.contains(articleNumber));
    }
}
