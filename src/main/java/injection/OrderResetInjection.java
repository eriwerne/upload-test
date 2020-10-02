package injection;

import application.orderreset.OrderResetHandler;
import dagger.Component;

import javax.inject.Singleton;

@SuppressWarnings("unused")
@Singleton
@Component(modules = DaggerInjectionModule.class)
public interface OrderResetInjection {
    void inject(OrderResetHandler orderResetHandler);
}
