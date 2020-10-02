package application.dataprocessing.matching;

import core.article.Article;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArticleDuplicateMatcher {
    public List<List<String>> matchDuplicatesGroups(List<Article> articles) {
        HashMap<Article, List<String>> duplicatesGroupArticleMap = makeDuplicatesGroupsForArticles(articles);
        return new ArrayList<>(duplicatesGroupArticleMap.values());
    }

    private HashMap<Article, List<String>> makeDuplicatesGroupsForArticles(List<Article> articles) {
        HashMap<Article, List<String>> duplicatesGroupArticleMap = new HashMap<>();
        for (Article article : articles) {
            List<String> duplicatesGroupForArticle = findMatchingDuplicatesGroupForArticle(duplicatesGroupArticleMap, article);
            if (duplicatesGroupForArticle == null) {
                duplicatesGroupForArticle = new ArrayList<>();
                duplicatesGroupArticleMap.put(article, duplicatesGroupForArticle);
            }
            duplicatesGroupForArticle.add(article.getArticleNumber());
        }
        return duplicatesGroupArticleMap;
    }

    private List<String> findMatchingDuplicatesGroupForArticle(HashMap<Article, List<String>> groupAttributesMap, Article article) {
        for (Article articleDuplicate : groupAttributesMap.keySet())
            if (articlesAreDuplicates(article, articleDuplicate))
                return groupAttributesMap.get(articleDuplicate);

        return null;
    }

    private boolean articlesAreDuplicates(Article article, Article otherArticle) {
        return article.isInImageGroupWith(otherArticle)
                && article.getMaterials().equals(otherArticle.getMaterials());
    }
}
