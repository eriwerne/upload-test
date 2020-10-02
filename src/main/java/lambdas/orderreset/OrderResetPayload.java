package lambdas.orderreset;

public class OrderResetPayload {
    private final String orderNumber;

    public OrderResetPayload(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }
}
