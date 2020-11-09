package devtools.apiqualityinspection;

import application.output.PersisterFailure;
import application.reading.articlereader.ArticleReader;
import application.reading.exception.ArticleNotFound;
import application.reading.exception.ResourceFailure;
import core.article.Article;
import core.article.exceptions.InvalidArticleData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArticleFunctionQualityInspector {
    private final ArticleReader articleReader;

    public ArticleFunctionQualityInspector() {
        articleReader = new ArticleReader();
    }

    public HashMap<String, HashMap<String, List<String>>> getArticleFunctions(List<String> articleNumbers) throws ResourceFailure, InvalidArticleData, ArticleNotFound, PersisterFailure {
        HashMap<String, HashMap<String, List<String>>> styleArticlesForFunctions = new HashMap<>();
        HashMap<String, Article> articles = articleReader.readArticles(articleNumbers);
        for (Article article : articles.values()) {
            String function = article.getArticleFunctionDescription();
            addFunctionToResultIfNotExists(styleArticlesForFunctions, function);
            addStyleToResultIfNotExists(styleArticlesForFunctions, article, function);
            addArticleToResult(styleArticlesForFunctions, article, function);
        }
        return styleArticlesForFunctions;
    }

    private void addArticleToResult(HashMap<String, HashMap<String, List<String>>> styleArticlesForFunctions, Article article, String function) {
        styleArticlesForFunctions.get(function).get(article.getStyleId()).add(article.getArticleNumber());
    }

    private void addStyleToResultIfNotExists(HashMap<String, HashMap<String, List<String>>> styleArticlesForFunctions, Article article, String function) {
        if (!styleArticlesForFunctions.get(function).containsKey(article.getStyleId()))
            styleArticlesForFunctions.get(function).put(article.getStyleId(), new ArrayList<>());
    }

    private void addFunctionToResultIfNotExists(HashMap<String, HashMap<String, List<String>>> styleArticlesForFunctions, String function) {
        if (!styleArticlesForFunctions.containsKey(function))
            styleArticlesForFunctions.put(function, new HashMap<>());
    }
}