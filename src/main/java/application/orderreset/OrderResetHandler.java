package application.orderreset;

import application.output.Persister;
import application.output.PersisterFailure;
import injection.DaggerOrderResetInjection;

import javax.inject.Inject;

public class OrderResetHandler {
    @Inject
    Persister persister;

    public OrderResetHandler() {
        DaggerOrderResetInjection.builder().build().inject(this);
    }

    public void resetOrder(String orderNumber) throws PersisterFailure {
        persister.removeFolder(orderNumber + "/");
        persister.removeFolder("cache/");
    }
}
