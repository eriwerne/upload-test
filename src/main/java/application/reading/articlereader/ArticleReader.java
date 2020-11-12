package application.reading.articlereader;

import application.reading.exception.ArticleNotFound;
import application.reading.exception.ResourceFailure;
import core.article.Article;
import core.article.exceptions.InvalidArticleData;
import injection.DaggerArticleReaderInjection;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;

public class ArticleReader {
    @Inject
    ArticleReaderSource articleReaderSource;

    public ArticleReader() {
        DaggerArticleReaderInjection.builder().build().inject(this);
    }

    public HashMap<String, Article> readArticles(List<String> articleNumbers) throws InvalidArticleData, ResourceFailure, ArticleNotFound {
        HashMap<String, Article> articles = new HashMap<>();
        List<Article> requestedArticles = articleReaderSource.readArticles(articleNumbers);
        requestedArticles.forEach(article -> articles.put(article.getArticleNumber(), article));
        return articles;
    }
}
