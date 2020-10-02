package utils.systemtest.fluentinterface;

import application.output.formats.OutputFormatType;
import utils.systemtest.SystemTestConfiguration;

public class SystemTestExpectation {
    private final SystemTestConfiguration systemTestConfiguration;

    public SystemTestExpectation(SystemTestConfiguration systemTestConfiguration) {
        this.systemTestConfiguration = systemTestConfiguration;
    }

    public SystemTestExpectationSetup expectationFileInGivenDirectory(String expectationFileName, OutputFormatType formatType) {
        systemTestConfiguration.addExpectationFileName(expectationFileName, formatType);
        return new SystemTestExpectationSetup(systemTestConfiguration);
    }

    public SystemTestExpectationSetup exception() {
        systemTestConfiguration.setExpectsException();
        return new SystemTestExpectationSetup(systemTestConfiguration);
    }
}
