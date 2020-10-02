package application.output.formats;

import core.order.Order;

public interface OutputFormat {
    String getOutputFromOrder(Order order) throws Exception;
}
