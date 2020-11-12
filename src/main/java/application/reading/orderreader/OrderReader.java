package application.reading.orderreader;

import application.reading.exception.OrderNotFound;
import application.reading.exception.ResourceFailure;
import injection.DaggerOrderReaderInjection;

import javax.inject.Inject;
import java.util.List;

public class OrderReader {
    @Inject
    OrderReaderSource orderReaderSource;

    public OrderReader() {
        DaggerOrderReaderInjection.builder().build().inject(this);
    }

    public List<String> readArticleNumbersInOrder(String orderNumber) throws OrderNotFound, ResourceFailure {
        return orderReaderSource.readArticleNumbersInOrder(orderNumber);
    }
}
