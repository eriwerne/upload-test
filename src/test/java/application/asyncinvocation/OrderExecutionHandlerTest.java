package application.asyncinvocation;

import application.ProcessMediator;
import application.exceptions.ApplicationFailed;
import application.output.Persister;
import application.output.PersisterFailure;
import injection.PersisterMock;
import org.junit.Before;
import org.junit.Test;
import utils.UnitTest;

import static org.mockito.Mockito.*;

public class OrderExecutionHandlerTest extends UnitTest {
    private OrderExecutionHandler cut;
    private String orderNumber;
    private String outputFilename;
    private String exceptionDocumentName;
    private String folderPath;
    private ProcessMediator mockProcessMediator;
    private Persister mockPersister;
    private String startedFlagFilename;

    @Before
    public void setup() {
        mockProcessMediator = mock(ProcessMediator.class);
        mockPersister = new PersisterMock().getPersisterMock();
        cut = new OrderExecutionHandler(mockProcessMediator);
        orderNumber = fixtureString();
        outputFilename = fixtureString();
        exceptionDocumentName = fixtureString();
        folderPath = fixtureString();
        startedFlagFilename = fixtureString();
    }

    @Test
    public void when_invocation_is_called_successfuly_then_processmediator_is_executed() throws ApplicationFailed {
        cut.executeOrder(orderNumber, outputFilename, exceptionDocumentName, folderPath, startedFlagFilename);
        verify(mockProcessMediator, times(1)).runOrderNumber(orderNumber, outputFilename, folderPath);
    }

    @Test
    public void when_invocation_is_called_successfuly_then_no_errorlog_is_created() throws PersisterFailure {
        cut.executeOrder(orderNumber, outputFilename, exceptionDocumentName, folderPath, startedFlagFilename);
        verify(mockPersister, times(0)).persistString(any(), eq(exceptionDocumentName), any());
    }

    @Test
    public void when_invocation_is_called_successfuly_then_startedflag_is_created() throws PersisterFailure {
        cut.executeOrder(orderNumber, outputFilename, exceptionDocumentName, folderPath, startedFlagFilename);
        verify(mockPersister, times(1)).persistString(folderPath, startedFlagFilename, "");
    }

    @Test
    public void when_invocation_is_called_successfuly_then_startedflag_is_deleted() throws PersisterFailure {
        cut.executeOrder(orderNumber, outputFilename, exceptionDocumentName, folderPath, startedFlagFilename);
        verify(mockPersister, times(1)).remove(folderPath, startedFlagFilename);
    }

    @Test
    public void when_invocation_is_called_and_an_error_occurs_then_an_errorlog_is_created() throws PersisterFailure, ApplicationFailed {
        doThrow(new RuntimeException()).when(mockProcessMediator).runOrderNumber(any(), eq(outputFilename), eq(folderPath));
        cut.executeOrder(orderNumber, outputFilename, exceptionDocumentName, folderPath, startedFlagFilename);
        verify(mockPersister, times(1)).persistString(eq(folderPath), eq(exceptionDocumentName), any());
    }
}
