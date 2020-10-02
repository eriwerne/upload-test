package application.dataprocessing.matching;

import core.article.Article;

import java.util.HashMap;

public class MirrorRelations {
    public HashMap<String, String> getMirrorOriginRelation() {
        return mirrorOriginRelation;
    }

    private final HashMap<String, String> mirrorOriginRelation = new HashMap<>();

    public void addMirrorArticle(Article article) {
        mirrorOriginRelation.put(article.getArticleNumber(), null);
    }

    public boolean hasArticleAsMirror(String article) {
        return mirrorOriginRelation.containsKey(article);
    }

    public void setOriginArticleForMirrorArticle(Article mirrorArticle, Article originArticle) {
        setOriginArticleForMirrorArticle(mirrorArticle.getArticleNumber(), originArticle.getArticleNumber());
    }

    public void setOriginArticleForMirrorArticle(String articleNumberMirror, String articleNumberOrigin) {
        mirrorOriginRelation.remove(articleNumberMirror);
        mirrorOriginRelation.put(articleNumberMirror, articleNumberOrigin);
    }

    public String getOriginArticleNumberForMirror(String articleNumber) {
        return mirrorOriginRelation.get(articleNumber);
    }

    public boolean hasRelationToOrigin(String articleNumber) {
        return mirrorOriginRelation.get(articleNumber) != null;
    }
}
