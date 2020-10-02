package application.reading.imageorderreader;

import application.reading.exception.OrderNotFound;
import application.reading.exception.ResourceFailure;
import core.image.ImageOrder;

import java.util.HashMap;
import java.util.List;

public interface ImageOrderReaderSource {
    HashMap<String, List<ImageOrder>> readImageOrdersForOrderNumber(String orderNumber) throws OrderNotFound, ResourceFailure;
}
