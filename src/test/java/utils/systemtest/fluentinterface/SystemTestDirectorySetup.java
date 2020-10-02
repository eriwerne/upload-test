package utils.systemtest.fluentinterface;

import utils.systemtest.SystemTestConfiguration;

public class SystemTestDirectorySetup {
    private final SystemTestConfiguration systemTestConfiguration;

    public SystemTestDirectorySetup(SystemTestConfiguration systemTestConfiguration) {
        this.systemTestConfiguration = systemTestConfiguration;
    }

    public SystemTestMockSetup mocksApisFromGivenDirectory() {
        return new SystemTestMockSetup(this.systemTestConfiguration);
    }
}
