package devtools.apiqualityinspection;

import application.reading.articlereader.ArticleReader;
import application.reading.exception.ArticleNotFound;
import application.reading.exception.ResourceFailure;
import core.article.Article;
import core.article.exceptions.InvalidArticleData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleMaterialQualityInspector {

    private final ArticleReader articleReader;

    public ArticleMaterialQualityInspector() {
        articleReader = new ArticleReader();
    }

    public Map<String, String> getInvalidArticleMaterialId(List<String> articleNumbers) throws ResourceFailure, InvalidArticleData, ArticleNotFound {
        Map<String, String> invalidArticleMaterialId = new HashMap<>();
        HashMap<String, Article> articles = articleReader.readArticles(articleNumbers);
        for (Article article : articles.values()) {
            String materialCode = article.getMaterials().getMaterialCode();
            if (materialCode == null || materialCode.contains(".jpg") || materialCode.contains(".jpeg")) {
                invalidArticleMaterialId.put(article.getArticleNumber(), materialCode);
            }
        }
        return invalidArticleMaterialId;
    }
}
