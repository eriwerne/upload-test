package utils.systemtest.fluentinterface;

import utils.systemtest.SystemTestConfiguration;

public class SystemTestOrderNumberSetup {

    private final SystemTestConfiguration systemTestConfiguration;

    public SystemTestOrderNumberSetup(SystemTestConfiguration systemTestConfiguration) {
        this.systemTestConfiguration = systemTestConfiguration;
    }

    public SystemTestDirectorySetup worksWithDirectory(String systemTestDirectory) {
        systemTestConfiguration.setSystemTestDirectory(systemTestDirectory);
        return new SystemTestDirectorySetup(systemTestConfiguration);
    }
}
