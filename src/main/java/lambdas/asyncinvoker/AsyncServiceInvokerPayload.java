package lambdas.asyncinvoker;

import application.output.formats.OutputFormatType;

public class AsyncServiceInvokerPayload {
    private final String orderNumber;
    private final OutputFormatType formatType;

    public AsyncServiceInvokerPayload(String orderNumber, OutputFormatType formatType) {
        this.orderNumber = orderNumber;
        this.formatType = formatType;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public OutputFormatType getFormatType() {
        return formatType;
    }
}
