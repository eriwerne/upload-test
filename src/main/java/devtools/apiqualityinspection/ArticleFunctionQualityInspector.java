package devtools.apiqualityinspection;

import application.reading.articlereader.ArticleReader;
import application.reading.exception.ArticleNotFound;
import application.reading.exception.ResourceFailure;
import core.article.Article;
import core.article.exceptions.InvalidArticleData;

import java.util.HashMap;
import java.util.List;

public class ArticleFunctionQualityInspector {
    private final ArticleReader articleReader;

    public ArticleFunctionQualityInspector() {
        articleReader = new ArticleReader();
    }

    public HashMap<String, HashMap<String, HashMap<String, String>>> getArticleFunctions(List<String> articleNumbers) throws ResourceFailure, InvalidArticleData, ArticleNotFound {
        HashMap<String, HashMap<String, HashMap<String, String>>> styleArticlesForFunctions = new HashMap<>();
        HashMap<String, Article> articles = articleReader.readArticles(articleNumbers);
        for (Article article : articles.values()) {
            String function = article.getArticleFunctionDescription();
            addFunctionToResultIfNotExists(styleArticlesForFunctions, function);
            addStyleToResultIfNotExists(styleArticlesForFunctions, article.getStyleId(), function);
            addArticleToResult(styleArticlesForFunctions, article, function);
        }
        return styleArticlesForFunctions;
    }

    private void addArticleToResult(HashMap<String, HashMap<String, HashMap<String, String>>> styleArticlesForFunctions, Article article, String function) {
        styleArticlesForFunctions.get(function).get(article.getStyleId()).put(article.getArticleNumber(), article.getName());
    }

    private void addStyleToResultIfNotExists(HashMap<String, HashMap<String, HashMap<String, String>>> styleArticlesForFunctions, String styleId, String function) {
        if (!styleArticlesForFunctions.get(function).containsKey(styleId))
            styleArticlesForFunctions.get(function).put(styleId, new HashMap<>());
    }

    private void addFunctionToResultIfNotExists(HashMap<String, HashMap<String, HashMap<String, String>>> styleArticlesForFunctions, String function) {
        if (!styleArticlesForFunctions.containsKey(function))
            styleArticlesForFunctions.put(function, new HashMap<>());
    }
}
