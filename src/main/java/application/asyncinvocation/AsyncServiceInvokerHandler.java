package application.asyncinvocation;

import application.output.Persister;
import application.output.PersisterFailure;
import application.output.formats.OutputFormatType;
import injection.DaggerAsyncServiceInvokerHandlerInjection;

import javax.inject.Inject;

public class AsyncServiceInvokerHandler {
    @Inject
    AsyncProcessCall asyncProcessCall;
    @Inject
    Persister persister;
    private String folderPath;
    private final String startedFlagFilename = "started";
    private final String exceptionDocumentName = "error";
    private String outputFilename;

    public AsyncServiceInvokerHandler() {
        DaggerAsyncServiceInvokerHandlerInjection.builder().build().inject(this);
    }

    public String requestOrder(String orderNumber, OutputFormatType formatType) {
        try {
            folderPath = orderNumber + "/";
            outputFilename = orderNumber;

            String response;
            if (outputFileExists(formatType)) {
                response = getOutputFileText(formatType);
            } else if (errorFileExists()) {
                response = getErrorFileText();
            } else if (startedFlagNotExists()) {
                asyncProcessCall.executeOrder(orderNumber, outputFilename, exceptionDocumentName, folderPath, startedFlagFilename);
                response = "Started";
            } else {
                response = "Busy";
            }
            return response;

        } catch (PersisterFailure e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private boolean startedFlagNotExists() throws PersisterFailure {
        return !persister.pathExists(folderPath, startedFlagFilename);
    }

    private String getErrorFileText() {
        try {
            return persister.getPersistedString(folderPath, exceptionDocumentName);
        } catch (PersisterFailure e) {
            e.printStackTrace();
            return "Could not read error log";
        }
    }

    private boolean errorFileExists() throws PersisterFailure {
        return persister.pathExists(folderPath, exceptionDocumentName);
    }

    private String getOutputFileText(OutputFormatType formatType) {
        try {
            return persister.getPersistedString(folderPath, outputFilename + formatType.getFileExtension());
        } catch (PersisterFailure e) {
            e.printStackTrace();
            return "Could not read file";
        }
    }

    private boolean outputFileExists(OutputFormatType formatType) throws PersisterFailure {
        return persister.pathExists(folderPath, outputFilename + formatType.getFileExtension());
    }
}
