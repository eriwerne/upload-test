package injection;

import application.reading.articlereader.ArticleReaderSource;

import static org.mockito.Mockito.mock;

public class ArticleReaderSourceMock {
    private final ArticleReaderSource articleReaderSource = mock(ArticleReaderSource.class);

    public ArticleReaderSourceMock() {
        DaggerInjectionModule.articleReaderSource = articleReaderSource;
    }

    public ArticleReaderSource getArticleReaderSourceMock() {
        return articleReaderSource;
    }
}
