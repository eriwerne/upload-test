package utils.systemtest.fluentinterface;

import utils.systemtest.SystemTestConfiguration;

public class SystemTest {

    private final SystemTestConfiguration systemTestConfiguration;

    public SystemTest() {
        this.systemTestConfiguration = new SystemTestConfiguration();
    }

    public SystemTestOrderNumberSetup forOrderNumber(String orderNumber) {
        systemTestConfiguration.setOrderNumber(orderNumber);
        return new SystemTestOrderNumberSetup(systemTestConfiguration);
    }
}
