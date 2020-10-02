package injection;

import application.reading.orderreader.OrderReaderSource;

import static org.mockito.Mockito.mock;

public class OrderReaderSourceMock {
    private final OrderReaderSource orderReaderSource = mock(OrderReaderSource.class);

    public OrderReaderSourceMock() {
        DaggerInjectionModule.orderReaderSource = orderReaderSource;
    }

    public OrderReaderSource getOrderReaderSourceMock() {
        return orderReaderSource;
    }
}
