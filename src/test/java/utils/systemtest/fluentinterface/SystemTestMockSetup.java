package utils.systemtest.fluentinterface;

import utils.systemtest.SystemTestConfiguration;

public class SystemTestMockSetup {
    private final SystemTestConfiguration systemTestConfiguration;

    public SystemTestMockSetup(SystemTestConfiguration systemTestConfiguration) {
        this.systemTestConfiguration = systemTestConfiguration;
    }

    public SystemTestExpectation andExpects() {
        return new SystemTestExpectation(systemTestConfiguration);
    }
}
