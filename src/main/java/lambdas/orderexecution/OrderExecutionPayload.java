package lambdas.orderexecution;

public class OrderExecutionPayload {
    private final String orderNumber;
    private final String outputFilename;
    private final String exceptionDocumentName;
    private final String folderPath;
    private final String startedFlagFilename;

    public OrderExecutionPayload(String orderNumber, String outputFilename, String exceptionDocumentName, String folderPath, String startedFlagFilename) {
        this.orderNumber = orderNumber;
        this.outputFilename = outputFilename;
        this.exceptionDocumentName = exceptionDocumentName;
        this.folderPath = folderPath;
        this.startedFlagFilename = startedFlagFilename;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getOutputFilename() {
        return outputFilename;
    }

    public String getExceptionDocumentName() {
        return exceptionDocumentName;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public String getStartedFlagFilename() {
        return startedFlagFilename;
    }
}
