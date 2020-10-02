package application.dataprocessing.merging;

import core.ArticleImageOrderCollection;
import core.image.ImageOrder;

import java.util.List;

public class ImageOrderDuplicateMerger {
    public void mergeFilenamesToDuplicateImageOrders(List<List<String>> articleDuplicatesGroups, ArticleImageOrderCollection articleImageOrders) {
        for (List<String> duplicatesGroup : articleDuplicatesGroups)
            for (String articleNumber : duplicatesGroup)
                exchangeFilenamesWithDuplicatesInGroup(articleImageOrders, duplicatesGroup, articleNumber);
    }

    private void exchangeFilenamesWithDuplicatesInGroup(ArticleImageOrderCollection articleImageOrders, List<String> duplicatesGroup, String articleNumber) {
        for (String articleNumberDuplicate : duplicatesGroup) {
            if (articleNumber.equals(articleNumberDuplicate))
                continue;
            for (ImageOrder imageOrder : articleImageOrders.getImageOrdersForArticle(articleNumber))
                if (imageOrderIsValidForDuplicates(imageOrder))
                    for (ImageOrder imageOrderDuplicate : articleImageOrders.getImageOrdersForArticle(articleNumberDuplicate))
                        if (imageOrder.equals(imageOrderDuplicate)) {
                            imageOrder.addDuplicateFilenames(imageOrderDuplicate);
                            imageOrderDuplicate.addDuplicateFilenames(imageOrder);
                        }
        }
    }

    private boolean imageOrderIsValidForDuplicates(ImageOrder imageOrderArticle) {
        //Function are per definition unique images and should not exchange filenames with other function images
        return !imageOrderArticle.isFunctionImage();
    }

}
