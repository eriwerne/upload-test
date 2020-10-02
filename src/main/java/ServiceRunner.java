import application.ProcessMediator;
import application.exceptions.ApplicationFailed;

class ServiceRunner {

    public static void main(String[] args) throws ApplicationFailed {
        String orderNumber = System.getenv("ORDER_NUMBER");
        new ProcessMediator().runOrderNumber(orderNumber, orderNumber, orderNumber + "/");
    }
}
