package systemtest.single_article_tests;

import application.exceptions.ApplicationFailed;
import application.output.formats.OutputFormatType;
import org.junit.Test;
import utils.systemtest.fluentinterface.SystemTest;

public class When_processing_image_order_with_one_article_with_function {
    @Test
    public void then_expect_given_expectation() throws ApplicationFailed {
        new SystemTest()
                .forOrderNumber("12345")
                .worksWithDirectory("systemtest/single_article_tests/one_mirror_one_container")
                .mocksApisFromGivenDirectory()
                .andExpects().expectationFileInGivenDirectory("exp.json", OutputFormatType.JSON)
                .andExpects().expectationFileInGivenDirectory("exp.csv", OutputFormatType.CSV)
                .whenExecutesWholeSystem();
    }
}
