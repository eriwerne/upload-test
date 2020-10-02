package infrastructure.mmp;

import application.reading.exception.ResourceFailure;

public interface MmpApi {
    String getImageOrdersForOrderNumber(String orderNumber) throws ResourceFailure;
}
