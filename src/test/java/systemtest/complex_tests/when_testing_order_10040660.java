package systemtest.complex_tests;

import application.exceptions.ApplicationFailed;
import application.output.formats.OutputFormatType;
import org.junit.Test;
import utils.systemtest.fluentinterface.SystemTest;

public class when_testing_order_10040660 {
    @Test
    public void then_expect_expectation() throws ApplicationFailed {
        new SystemTest()
                .forOrderNumber("10040660")
                .worksWithDirectory("systemtest/complex_tests/example_order_10040660")
                .mocksApisFromGivenDirectory()
                .andExpects().expectationFileInGivenDirectory("exp.json", OutputFormatType.JSON)
                .andExpects().expectationFileInGivenDirectory("exp.csv", OutputFormatType.CSV)
                .whenExecutesWholeSystem();
    }
}
