package application.reading.orderreader;

import application.reading.exception.OrderNotFound;
import application.reading.exception.ResourceFailure;
import injection.OrderReaderSourceMock;
import org.junit.Before;
import org.junit.Test;
import utils.UnitTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class OrderReaderTest extends UnitTest {
    private OrderReader cut;
    private OrderReaderSource mockOrderReaderSource;
    private String orderNumber;
    private final String articleNumber = "123456";

    @Before
    public void setup() throws ResourceFailure, OrderNotFound {
        mockOrderReaderSource = new OrderReaderSourceMock().getOrderReaderSourceMock();
        cut = new OrderReader();
        orderNumber = fixtureString();
        ArrayList<String> articleNumbers = new ArrayList<>();
        articleNumbers.add(articleNumber);
        when(mockOrderReaderSource.readArticleNumbersInOrder(orderNumber)).thenReturn(articleNumbers);
    }

    @Test
    public void when_order_reader_is_executed_and_persisted_result_not_exists_then_reader_source_is_requesetd() throws ResourceFailure, OrderNotFound {
        cut.readArticleNumbersInOrder(orderNumber);
        verify(mockOrderReaderSource, times(1)).readArticleNumbersInOrder(orderNumber);
    }

    @Test
    public void when_order_reader_is_executed_and_persisted_result_not_exists_then_result_is_returned() throws ResourceFailure, OrderNotFound {
        List<String> act = cut.readArticleNumbersInOrder(orderNumber);
        assertTrue(act.contains(articleNumber));
    }
}
