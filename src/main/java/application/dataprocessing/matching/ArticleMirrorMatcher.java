package application.dataprocessing.matching;

import core.article.Article;

import java.util.List;

public class ArticleMirrorMatcher {

    public MirrorRelations getMirrorRelations(List<Article> articles) {
        MirrorRelations mirrorRelations = new MirrorRelations();
        for (Article article : articles) {
            if (!article.isMirror())
                continue;
            mirrorRelations.addMirrorArticle(article);
            Article articleOrigin = findOriginArticle(articles, article);
            if (articleOrigin != null) {
                mirrorRelations.setOriginArticleForMirrorArticle(article, articleOrigin);
            }
        }
        return mirrorRelations;
    }

    private Article findOriginArticle(List<Article> articles, Article articleMirror) {
        for (Article otherArticle : articles)
            if (isOriginOf(otherArticle, articleMirror))
                return otherArticle;
        return null;
    }


    private boolean isOriginOf(Article otherArticle, Article articleMirror) {
        return (!otherArticle.isMirror() && articleMirror.isMirror())
                && otherArticle.isInImageGroupWith(articleMirror)
                && otherArticle.getMaterials().equals(articleMirror.getMaterials())
                && ((otherArticle.getArticleFunctions() == null) == (articleMirror.getArticleFunctions() == null))
                && ((otherArticle.getArticleFunctions() == null) ? articleMirror.getArticleFunctions() == null : otherArticle.getArticleFunctions().equals(articleMirror.getArticleFunctions()));
    }
}
