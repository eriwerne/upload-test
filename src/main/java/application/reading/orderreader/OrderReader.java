package application.reading.orderreader;

import application.output.Persister;
import application.output.PersisterFailure;
import application.reading.exception.OrderNotFound;
import application.reading.exception.ResourceFailure;
import injection.DaggerOrderReaderInjection;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class OrderReader {
    @Inject
    OrderReaderSource orderReaderSource;
    @Inject
    Persister persister;
    private final String downloadFolder = "cache/order/";

    public OrderReader() {
        DaggerOrderReaderInjection.builder().build().inject(this);
    }

    public List<String> readArticleNumbersInOrder(String orderNumber) throws OrderNotFound, ResourceFailure, PersisterFailure {
        ArrayList<String> articleNumbers;
        if (!persister.pathExists(downloadFolder, orderNumber)) {
            articleNumbers = orderReaderSource.readArticleNumbersInOrder(orderNumber);
            persister.persistObject(downloadFolder, orderNumber, articleNumbers);
        } else {
            //noinspection unchecked
            articleNumbers = (ArrayList<String>) persister.getObject(downloadFolder, orderNumber);
        }
        return articleNumbers;
    }
}
