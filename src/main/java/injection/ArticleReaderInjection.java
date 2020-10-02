package injection;

import application.reading.articlereader.ArticleReader;
import dagger.Component;

import javax.inject.Singleton;

@SuppressWarnings("unused")
@Singleton
@Component(modules = DaggerInjectionModule.class)
public interface ArticleReaderInjection {
    void inject(ArticleReader articleReader);
}
