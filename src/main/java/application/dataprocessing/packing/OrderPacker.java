package application.dataprocessing.packing;

import core.ArticleImageOrderCollection;
import core.article.Article;
import core.article.ImageGroupCharacteristics;
import core.article.Materials;
import core.image.ImageOrder;
import core.order.ImageGroupOrder;
import core.order.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderPacker {
    public Order createOrder(String orderNumber, ArticleImageOrderCollection imageOrders) {
        HashMap<Integer, ImageGroupOrder> imageGroupOrders = appendImageOrdersToStyleOrders(imageOrders);
        List<ImageGroupOrder> sortedStyleOrders = sortList(imageGroupOrders);
        return new Order(orderNumber, sortedStyleOrders, imageOrders.getFilenameArticleRelations());
    }

    private HashMap<Integer, ImageGroupOrder> appendImageOrdersToStyleOrders(ArticleImageOrderCollection imageOrders) {
        HashMap<Integer, ImageGroupOrder> imageGroupOrders = new HashMap<>();
        for (ImageGroupCharacteristics imageGroup : imageOrders.getImageGroupCharacteristics()) {
            int imageGroupHashCode = imageGroup.hashCode();
            ImageGroupOrder imageGroupOrder = new ImageGroupOrder(imageGroup.getStyleId(), imageGroup.getSizeAsString());
            imageGroupOrders.put(imageGroupHashCode, imageGroupOrder);

            for (Materials materials : imageOrders.getMaterialsForImageGroupHash(imageGroupHashCode))
                for (String function : imageOrders.getFunctionsForImageGroupHash(imageGroupHashCode))
                    imageGroupOrder.appendImageOrders(materials, function, getImageOrdersForImageGroupHashAndMaterialAndFunction(imageGroupHashCode, materials, function, imageOrders));
        }
        return imageGroupOrders;
    }

    private List<ImageGroupOrder> sortList(HashMap<Integer, ImageGroupOrder> imageGroupOrders) {
        List<ImageGroupOrder> sortedImageGroupOrders = new ArrayList<>();
        List<String> styleIds = new ArrayList<>();

        for (ImageGroupOrder groupOrder : imageGroupOrders.values())
            if (!styleIds.contains(groupOrder.getStyleId()))
                styleIds.add(groupOrder.getStyleId());

        styleIds.sort(String::compareTo);

        for (String styleId : styleIds)
            for (ImageGroupOrder imageGroupOrder : imageGroupOrders.values())
                if (imageGroupOrder.getStyleId().equals(styleId))
                    sortedImageGroupOrders.add(imageGroupOrder);

        return sortedImageGroupOrders;
    }

    private List<ImageOrder> getImageOrdersForImageGroupHashAndMaterialAndFunction(int imageGroupHash, Materials materials, String function, ArticleImageOrderCollection articleImageOrders) {
        ArrayList<ImageOrder> imageOrders = new ArrayList<>();
        for (Article article : articleImageOrders.getArticlesAsList()) {
            if (!(article.getImageGroup().hashCode() == imageGroupHash && article.getMaterials().equals(materials)))
                continue;
            for (ImageOrder imageOrder : articleImageOrders.getImageOrdersForArticle(article.getArticleNumber())) {
                if (article.getArticleFunctionDescription().equals(function))
                    imageOrders.add(imageOrder);
            }
        }
        return imageOrders;
    }
}


