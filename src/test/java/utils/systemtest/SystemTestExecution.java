package utils.systemtest;

import application.ProcessMediator;
import application.exceptions.ApplicationFailed;
import application.output.formats.OutputFormatType;
import injection.M2Mock;
import injection.MmpMock;
import injection.PersisterMock;
import utils.*;

import java.io.IOException;
import java.util.HashMap;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class SystemTestExecution {
    private final SystemTestConfiguration systemTestConfiguration;
    private String orderNumber;
    private final M2Mock m2Mock;
    private final MmpMock mmpMock;

    public SystemTestExecution(SystemTestConfiguration systemTestConfiguration) {
        m2Mock = new M2Mock();
        mmpMock = new MmpMock();
        new PersisterMock();
        this.systemTestConfiguration = systemTestConfiguration;
    }

    public void runTest() throws ApplicationFailed {
        try {
            orderNumber = systemTestConfiguration.getOrderNumber();

            //Mock
            mockMmpApiResponse();
            mockM2ApiResponse();

            //Act
            HashMap<OutputFormatType, String> act = getActFromService();

            //Expect
            if (systemTestConfiguration.expectsException())
                fail("Exception was not thrown");
            expectActIsEqualToFile(act);
        } catch (IOException e) {
            throw new ApplicationFailed(e);
        }
    }

    private void mockMmpApiResponse() {
        mmpMock.mockMmpApiResponse(
                systemTestConfiguration.getSystemTestDirectory() + "/api/mmp/imageorders/" + orderNumber + ".json",
                orderNumber);
    }

    private void mockM2ApiResponse() {
        try {
            String[] filenames = ResourceFileReader.getFilenamesInResourceFolder(
                    systemTestConfiguration.getSystemTestDirectory()
                            + "/api/m2/articles/");
            for (String filename : filenames) {
                String articleNumber = filename.replace(".json", "");
                String filePath = systemTestConfiguration.getSystemTestDirectory()
                        + "/api/m2/articles/"
                        + articleNumber + ".json";
                m2Mock.mockM2ApiResponse(filePath, articleNumber);
            }
        } catch (ResourceNotFound e) {
            //nothing to mock
        }
    }

    private HashMap<OutputFormatType, String> getActFromService() throws ApplicationFailed {
        ProcessMediator processMediator = new ProcessMediator();
        processMediator.runOrderNumber(orderNumber, "exp", "/Users/eriwerne/IdeaProjects/order-extractor/src/test/resources/"+systemTestConfiguration.getSystemTestDirectory()+"/expectation");
        return processMediator.getOutputStrings();
    }

    private void expectActIsEqualToFile(HashMap<OutputFormatType, String> act) throws IOException {
        for (OutputFormatType formatType : systemTestConfiguration.getExpectationFiles().keySet()) {
            String expectionFilename = systemTestConfiguration.getExpectationFiles().get(formatType);
            String expectedOutput = ResourceFileReader.readResource(systemTestConfiguration.getSystemTestDirectory() + "/expectation/" + expectionFilename);
            String actualOutput = act.get(formatType);
            assertEquals(expectedOutput, actualOutput);
        }
    }
}
