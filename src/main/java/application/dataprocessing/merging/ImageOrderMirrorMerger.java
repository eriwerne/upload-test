package application.dataprocessing.merging;

import application.dataprocessing.matching.MirrorRelations;
import core.ArticleImageOrderCollection;
import core.article.Article;
import core.image.ImageOrder;

import java.util.Iterator;

public class ImageOrderMirrorMerger {
    public void mergeFilenamesToMirrorImageOrders(MirrorRelations mirrorRelations, ArticleImageOrderCollection articleImageOrders) {
        for (String articleNumber : articleImageOrders.getArticleNumbers()) {
            if (mirrorRelations.hasArticleAsMirror(articleNumber)) {
                moveFilenamesForMirrorableImageOrdersToOrigin(mirrorRelations, articleNumber, articleImageOrders);
            }
        }
    }

    public void moveFilenamesOfMirrorArticles(ArticleImageOrderCollection articleImageOrders) {
        for (Article article : articleImageOrders.getArticlesAsList()) {
            if (article.isMirror())
                for (ImageOrder imageOrder : articleImageOrders.getImageOrdersForArticle(article.getArticleNumber())) {
                    if (imageOrder.getDetailfotografie().isMirrorable()) {
                        imageOrder.getFilenamesMirror().addAll(imageOrder.getFilenames());
                        imageOrder.getFilenames().clear();
                    }
                }
        }
    }

    private void moveFilenamesForMirrorableImageOrdersToOrigin(MirrorRelations mirrorRelations, String articleNumber, ArticleImageOrderCollection mergedImageOrders) {
        if (mirrorRelations.hasRelationToOrigin(articleNumber)) {
            String originArticleNumber = mirrorRelations.getOriginArticleNumberForMirror(articleNumber);
            Iterator<ImageOrder> imageOrders = mergedImageOrders.getImageOrdersForArticleAsIterator(articleNumber);
            while (imageOrders.hasNext()) {
                ImageOrder imageOrder = imageOrders.next();
                ImageOrder originImageOrder = findOriginImageOrder(imageOrder, mergedImageOrders, originArticleNumber);
                if (originImageOrder != null) {
                    originImageOrder.addDuplicateFilenames(imageOrder);
                    imageOrders.remove();
                }
            }
        }
    }

    private ImageOrder findOriginImageOrder(ImageOrder imageOrder, ArticleImageOrderCollection imageOrders, String originArticleNumber) {
        for (ImageOrder originImageOrder : imageOrders.getImageOrdersForArticle(originArticleNumber))
            if (imageOrder.equals(originImageOrder))
                return originImageOrder;
        return null;
    }
}
