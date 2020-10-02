package injection;

import application.reading.orderreader.OrderReader;
import dagger.Component;

import javax.inject.Singleton;

@SuppressWarnings("unused")
@Singleton
@Component(modules = DaggerInjectionModule.class)
public interface OrderReaderInjection {
    void inject(OrderReader orderReader);
}
