package application.asyncinvocation;

public interface AsyncProcessCall {
    void executeOrder(String orderNumber, String outputFilename, String exceptionDocumentName, String folderPath, String startedFlagFilename);
}
