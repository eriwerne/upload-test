package application.asyncinvocation;

import application.output.Persister;
import application.output.PersisterFailure;
import injection.AsyncProcessStarterMock;
import injection.PersisterMock;
import org.junit.Before;
import org.junit.Test;
import utils.UnitTest;

import static application.output.formats.OutputFormatType.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AsyncServiceInvokerLambdaHandlerTest extends UnitTest {
    private AsyncServiceInvokerHandler cut;
    private String folderPath;
    private Persister mockPersister;
    private String exceptionDocumentName;
    private String startedFlagFilename;
    private String orderNumber;
    private String filename;

    private void mockStartedFlagExists() throws PersisterFailure {
        when(mockPersister.pathExists(folderPath, startedFlagFilename)).thenReturn(true);
    }

    private void mockJsonFile(String result) throws PersisterFailure {
        when(mockPersister.pathExists(folderPath, filename + ".json")).thenReturn(true);
        when(mockPersister.getPersistedString(folderPath, filename + JSON.getFileExtension())).thenReturn(result);
    }

    private void mockErrorFile(String error) throws PersisterFailure {
        when(mockPersister.pathExists(folderPath, exceptionDocumentName)).thenReturn(true);
        when(mockPersister.getPersistedString(folderPath, exceptionDocumentName)).thenReturn(error);
    }

    @Before
    public void setup() {
        mockPersister = new PersisterMock().getPersisterMock();
        new AsyncProcessStarterMock();
        cut = new AsyncServiceInvokerHandler();
        orderNumber = fixtureString();
        folderPath = orderNumber + "/";
        filename = orderNumber;
        exceptionDocumentName = "error";
        startedFlagFilename = "started";
    }

    @Test
    public void when_service_is_called_for_an_order_where_no_file_exists_then_assert_service_started() {
        String message = cut.requestOrder(orderNumber, JSON);
        assertEquals("Started", message);
    }

    @Test
    public void when_service_is_called_for_an_order_where_busy_state_exists_then_assert_busy() throws PersisterFailure {
        mockStartedFlagExists();

        String message = cut.requestOrder(orderNumber, JSON);
        assertEquals("Busy", message);
    }

    @Test
    public void when_service_is_called_for_an_order_where_folder_contains_result_then_assert_result() throws PersisterFailure {
        String result = "Result";
        mockStartedFlagExists();
        mockJsonFile(result);

        String message = cut.requestOrder(orderNumber, JSON);
        assertEquals(result, message);
    }

    @Test
    public void when_service_is_called_for_an_order_where_folder_contains_result_and_no_started_flag_then_assert_result() throws PersisterFailure {
        String result = "Result";
        mockJsonFile(result);

        String message = cut.requestOrder(orderNumber, JSON);
        assertEquals(result, message);
    }

    @Test
    public void when_service_is_called_for_an_order_where_folder_contains_error_then_assert_error() throws PersisterFailure {
        String error = "Error";
        mockStartedFlagExists();
        mockErrorFile(error);

        String message = cut.requestOrder(orderNumber, JSON);
        assertEquals(error, message);
    }

    @Test
    public void when_service_is_called_for_an_order_where_folder_contains_error_and_no_started_flag_then_assert_error() throws PersisterFailure {
        String error = "Error";
        mockErrorFile(error);

        String message = cut.requestOrder(orderNumber, JSON);
        assertEquals(error, message);
    }

    @Test
    public void when_service_is_called_for_csv_then_csv_file_is_requested() {
        String message = cut.requestOrder(orderNumber, CSV);
        assertEquals("Started", message);
    }

    @Test
    public void when_service_is_called_for_csv_and_json_file_exists_then_csv_file_is_requested() throws PersisterFailure {
        mockJsonFile(fixtureString());

        String message = cut.requestOrder(orderNumber, CSV);
        assertEquals("Started", message);
    }
}
