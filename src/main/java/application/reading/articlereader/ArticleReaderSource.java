package application.reading.articlereader;

import application.reading.exception.ArticleNotFound;
import application.reading.exception.ResourceFailure;
import core.article.Article;
import core.article.exceptions.InvalidArticleData;

import java.util.List;

public interface ArticleReaderSource {
    List<Article> readArticles(List<String> articleNumbers) throws ArticleNotFound, InvalidArticleData, ResourceFailure;
}
