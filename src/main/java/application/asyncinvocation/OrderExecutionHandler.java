package application.asyncinvocation;

import application.ProcessMediator;
import application.output.Persister;
import application.output.PersisterFailure;
import injection.DaggerProcessMediatorStarterHandlerInjection;

import javax.inject.Inject;
import java.io.PrintWriter;
import java.io.StringWriter;

public class OrderExecutionHandler {
    @Inject
    Persister persister;
    private final ProcessMediator processMediator;

    public OrderExecutionHandler(ProcessMediator processMediator) {
        DaggerProcessMediatorStarterHandlerInjection.builder().build().inject(this);
        this.processMediator = processMediator;
    }

    public void executeOrder(String orderNumber, String outputFilename, String exceptionDocumentName, String folderPath, String startedFlagFilename) {
        try {
            persister.persistString(folderPath, startedFlagFilename, "");
            processMediator.runOrderNumber(orderNumber, outputFilename, folderPath);
        } catch (Throwable e) {
            e.printStackTrace();
            persistExceptionAsResponseDocument(exceptionDocumentName, folderPath, e);
        } finally {
            removeStartedFlag(folderPath, startedFlagFilename);
        }
    }

    private void removeStartedFlag(String folderPath, String startedFlagFilename) {
        try {
            persister.remove(folderPath, startedFlagFilename);
        } catch (PersisterFailure e) {
            e.printStackTrace();
        }
    }

    private void persistExceptionAsResponseDocument(String exceptionDocumentName, String folderPath, Throwable e) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String message = "";
            message += e + "\n";
            message += sw.toString();
            persister.persistString(folderPath, exceptionDocumentName, message);
        } catch (PersisterFailure persisterFailure) {
            persisterFailure.printStackTrace();
        }
    }
}
