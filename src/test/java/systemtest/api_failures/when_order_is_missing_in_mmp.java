package systemtest.api_failures;

import application.exceptions.ApplicationFailed;
import org.junit.Test;
import utils.systemtest.fluentinterface.SystemTest;

public class when_order_is_missing_in_mmp {
    @Test(expected = ApplicationFailed.class)
    public void then_expect_exception() throws ApplicationFailed {
        new SystemTest()
                .forOrderNumber("12345")
                .worksWithDirectory("systemtest/api_failures/order_not_found_on_mmp")
                .mocksApisFromGivenDirectory()
                .andExpects().exception()
                .whenExecutesWholeSystem();
    }
}
