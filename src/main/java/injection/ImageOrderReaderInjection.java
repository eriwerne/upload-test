package injection;

import application.reading.imageorderreader.ImageOrderReader;
import dagger.Component;

import javax.inject.Singleton;

@SuppressWarnings("unused")
@Singleton
@Component(modules = DaggerInjectionModule.class)
public interface ImageOrderReaderInjection {
    void inject(ImageOrderReader imageOrderReader);
}
