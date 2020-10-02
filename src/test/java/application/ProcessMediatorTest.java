package application;

import application.exceptions.ApplicationFailed;
import application.output.Persister;
import application.output.PersisterFailure;
import application.reading.exception.ResourceFailure;
import infrastructure.m2.M2Api;
import org.junit.Before;
import org.junit.Test;
import injection.M2Mock;
import injection.MmpMock;
import injection.PersisterMock;
import utils.UnitTest;

import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ProcessMediatorTest extends UnitTest {
    private ProcessMediator cut;
    private Persister mockPersister;
    private String orderNumber;
    private String filename;
    private String foldername;
    private M2Api m2Api;

    @Before
    public void setup() {
        M2Mock m2Mock = new M2Mock();
        MmpMock mmpMock = new MmpMock();
        mockPersister = new PersisterMock().getPersisterMock();
        cut = new ProcessMediator();
        orderNumber = fixtureString();
        filename = fixtureString();
        foldername = fixtureString();

        m2Mock.mockM2ApiResponse("systemtest/single_article_tests/one_article_one_container/api/m2/articles/123456.json", "123456");
        mmpMock.mockMmpApiResponse("systemtest/single_article_tests/one_article_one_container/api/mmp/imageorders/12345.json", orderNumber);
        m2Api = m2Mock.getM2Mock();
    }

    @Test
    public void when_order_is_executed_then_json_is_created() throws ApplicationFailed, PersisterFailure {
        cut.runOrderNumber(orderNumber, filename, foldername);
        verify(mockPersister, times(1)).persistString(eq(foldername), eq(filename + ".json"), any());
    }

    @Test
    public void when_order_is_executed_then_m2_is_requested() throws ApplicationFailed, ResourceFailure {
        cut.runOrderNumber(orderNumber, filename, foldername);
        verify(m2Api, times(1)).getArticleString(any());
    }
}
