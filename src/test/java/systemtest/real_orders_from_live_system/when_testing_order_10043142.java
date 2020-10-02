package systemtest.real_orders_from_live_system;

import application.exceptions.ApplicationFailed;
import application.output.formats.OutputFormatType;
import org.junit.Test;
import utils.systemtest.fluentinterface.SystemTest;

public class when_testing_order_10043142 {
    @Test
    public void then_expect_expectation() throws ApplicationFailed {
        new SystemTest()
                .forOrderNumber("10043142")
                .worksWithDirectory("systemtest/real_orders_from_live_system/10043142")
                .mocksApisFromGivenDirectory()
                .andExpects().expectationFileInGivenDirectory("exp.json", OutputFormatType.JSON)
                .andExpects().expectationFileInGivenDirectory("exp.csv", OutputFormatType.CSV)
                .whenExecutesWholeSystem();
    }
}
