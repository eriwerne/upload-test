package injection;

import dagger.Component;
import infrastructure.m2.M2;

import javax.inject.Singleton;

@SuppressWarnings("unused")
@Singleton
@Component(modules = DaggerInjectionModule.class)
public interface M2Injection {
    void inject(M2 m2);
}
