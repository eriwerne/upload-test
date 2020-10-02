package application.dataprocessing.filtering;

import core.ArticleImageOrderCollection;
import core.article.Article;
import core.image.ImageOrder;

import java.util.Iterator;

public class FilterForNonFunctionArticles {
    public void filter(ArticleImageOrderCollection articleImageOrders) {
        for (Article article : articleImageOrders.getArticlesAsList()) {
            Iterator<ImageOrder> imageOrders = articleImageOrders.getImageOrdersForArticleAsIterator(article);
            while (imageOrders.hasNext()) {
                ImageOrder imageOrder = imageOrders.next();
                if (!isValidImageOrder(article, imageOrder))
                    imageOrders.remove();
            }
        }
    }

    private boolean isValidImageOrder(Article article, ImageOrder imageOrder) {
        return isNotAFunctionImageForNonFunctionArticle(article, imageOrder);
    }

    private boolean isNotAFunctionImageForNonFunctionArticle(Article article, ImageOrder imageOrder) {
        return !(!article.hasFunction() && imageOrder.isFunctionImage());
    }
}
