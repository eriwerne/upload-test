package injection;

import application.asyncinvocation.OrderExecutionHandler;
import dagger.Component;

import javax.inject.Singleton;

@SuppressWarnings("unused")
@Singleton
@Component(modules = DaggerInjectionModule.class)
public interface ProcessMediatorStarterHandlerInjection {
    void inject(OrderExecutionHandler orderExecutionHandler);
}
