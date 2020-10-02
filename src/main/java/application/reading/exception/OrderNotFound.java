package application.reading.exception;

public class OrderNotFound extends Exception {

    public OrderNotFound(String orderNumber) {
        super("Order number " + orderNumber + " was not found");
    }
}
