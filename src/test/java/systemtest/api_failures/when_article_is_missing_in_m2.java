package systemtest.api_failures;

import application.exceptions.ApplicationFailed;
import org.junit.Test;
import utils.systemtest.fluentinterface.SystemTest;

public class when_article_is_missing_in_m2 {
    @Test(expected = ApplicationFailed.class)
    public void then_expect_exception() throws ApplicationFailed {
        new SystemTest()
                .forOrderNumber("12345")
                .worksWithDirectory("systemtest/api_failures/article_not_found_on_m2")
                .mocksApisFromGivenDirectory()
                .andExpects().exception()
                .whenExecutesWholeSystem();
    }
}
