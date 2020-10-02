package injection;

import application.output.OutputGenerator;
import dagger.Component;

import javax.inject.Singleton;

@SuppressWarnings("unused")
@Singleton
@Component(modules = DaggerInjectionModule.class)
public interface OutputGeneratorInjection {
    void inject(OutputGenerator outputGenerator);
}
