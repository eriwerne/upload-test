package injection;

import dagger.Component;
import infrastructure.mmp.Mmp;

import javax.inject.Singleton;

@SuppressWarnings("unused")
@Singleton
@Component(modules = DaggerInjectionModule.class)
public interface MmpInjection {
    void inject(Mmp mmp);
}
