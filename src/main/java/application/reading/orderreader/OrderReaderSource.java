package application.reading.orderreader;

import application.reading.exception.OrderNotFound;
import application.reading.exception.ResourceFailure;

import java.util.ArrayList;

public interface OrderReaderSource {
    ArrayList<String> readArticleNumbersInOrder(String orderNumber) throws OrderNotFound, ResourceFailure;
}
