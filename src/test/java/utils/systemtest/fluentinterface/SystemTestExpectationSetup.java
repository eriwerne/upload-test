package utils.systemtest.fluentinterface;

import application.exceptions.ApplicationFailed;
import utils.systemtest.SystemTestConfiguration;
import utils.systemtest.SystemTestExecution;

public class SystemTestExpectationSetup {
    private final SystemTestExecution systemTestExecution;
    private final SystemTestConfiguration systemTestConfiguration;

    public SystemTestExpectationSetup(SystemTestConfiguration systemTestConfiguration) {
        systemTestExecution = new SystemTestExecution(systemTestConfiguration);
        this.systemTestConfiguration = systemTestConfiguration;
    }

    public SystemTestExpectation andExpects() {
        return new SystemTestExpectation(systemTestConfiguration);
    }

    public void whenExecutesWholeSystem() throws ApplicationFailed {
        systemTestExecution.runTest();
    }
}
