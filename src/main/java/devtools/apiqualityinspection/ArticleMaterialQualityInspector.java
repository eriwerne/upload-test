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

public class ArticleMaterialQualityInspector {

    private final ArticleReader articleReader;

    public ArticleMaterialQualityInspector() {
        articleReader = new ArticleReader();
    }

    public List<String> getArticleNumbersWithInvalidMaterial(List<String> articleNumbers) throws ResourceFailure, InvalidArticleData, ArticleNotFound, PersisterFailure {
        ArrayList<String> invalidArticles = new ArrayList<>();
        HashMap<String, Article> articles = articleReader.readArticles(articleNumbers);
        for (Article article : articles.values())
            if (article.getMaterials().getMaterialCode() == null)
                invalidArticles.add(article.getArticleNumber());
        return invalidArticles;
    }
}
