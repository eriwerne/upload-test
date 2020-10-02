package injection;

import application.asyncinvocation.AsyncServiceInvokerHandler;
import dagger.Component;

import javax.inject.Singleton;

@SuppressWarnings("unused")
@Singleton
@Component(modules = DaggerInjectionModule.class)
public interface AsyncServiceInvokerHandlerInjection {
    void inject(AsyncServiceInvokerHandler asyncServiceInvokerHandler);
}
