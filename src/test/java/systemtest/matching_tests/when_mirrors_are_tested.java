package systemtest.matching_tests;

import application.exceptions.ApplicationFailed;
import application.output.formats.OutputFormatType;
import org.junit.Test;
import utils.systemtest.fluentinterface.SystemTest;

public class when_mirrors_are_tested {
    @Test
    public void then_mirrors_are_matched() throws ApplicationFailed {
        new SystemTest()
                .forOrderNumber("12345")
                .worksWithDirectory("systemtest/matching_tests/mirrors/two_mirrors_with_functions")
                .mocksApisFromGivenDirectory()
                .andExpects().expectationFileInGivenDirectory("exp.json", OutputFormatType.JSON)
                .andExpects().expectationFileInGivenDirectory("exp.csv", OutputFormatType.CSV)
                .whenExecutesWholeSystem();
    }
    @Test
    public void then_articles_with_different_colors_are_not_matched() throws ApplicationFailed {
        new SystemTest()
                .forOrderNumber("12345")
                .worksWithDirectory("systemtest/matching_tests/mirrors/two_articles_with_different_colors")
                .mocksApisFromGivenDirectory()
                .andExpects().expectationFileInGivenDirectory("exp.json", OutputFormatType.JSON)
                .andExpects().expectationFileInGivenDirectory("exp.csv", OutputFormatType.CSV)
                .whenExecutesWholeSystem();
    }
    @Test
    public void then_articles_with_different_styles_are_not_matched() throws ApplicationFailed {
        new SystemTest()
                .forOrderNumber("12345")
                .worksWithDirectory("systemtest/matching_tests/mirrors/two_articles_with_different_styles")
                .mocksApisFromGivenDirectory()
                .andExpects().expectationFileInGivenDirectory("exp.json", OutputFormatType.JSON)
                .andExpects().expectationFileInGivenDirectory("exp.csv", OutputFormatType.CSV)
                .whenExecutesWholeSystem();
    }
}
