package application.reading.articlereader;

import application.output.PersisterFailure;
import application.reading.exception.ArticleNotFound;
import application.reading.exception.ResourceFailure;
import core.article.Article;
import core.article.exceptions.InvalidArticleData;
import injection.DaggerArticleReaderInjection;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArticleReader {
    @Inject
    ArticleReaderSource articleReaderSource;
    private final ArticleCache articleCache;

    public ArticleReader() {
        DaggerArticleReaderInjection.builder().build().inject(this);
        articleCache = new ArticleCache();
    }

    public HashMap<String, Article> readArticles(List<String> articleNumbers) throws InvalidArticleData, ResourceFailure, ArticleNotFound, PersisterFailure {
        articleCache.initArticles(articleNumbers);
        ArrayList<String> requestArticleNumbers = articleCache.getNotCachedArticlesFromList(articleNumbers);
        if (!requestArticleNumbers.isEmpty()) {
            List<Article> requestedArticles = articleReaderSource.readArticles(requestArticleNumbers);
            articleCache.putArticles(requestedArticles);
        }
        return articleCache.getCachedArticlesFromList(articleNumbers);
    }
}
