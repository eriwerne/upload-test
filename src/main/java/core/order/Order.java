package core.order;

import core.article.Article;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class Order implements Serializable {
    private final String orderNumber;
    private final HashMap<String, Article> filenameArticleRelations;
    private final List<ImageGroupOrder> imageGroupOrders;

    public Order(String orderNumber, List<ImageGroupOrder> imageGroupOrders, HashMap<String, Article> filenameArticleRelations) {
        this.orderNumber = orderNumber;
        this.filenameArticleRelations = filenameArticleRelations;
        this.imageGroupOrders = imageGroupOrders;
    }

    public List<ImageGroupOrder> getImageGroupOrders() {
        return imageGroupOrders;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public HashMap<String, Article> getFilenameArticleRelations() {
        return filenameArticleRelations;
    }
}
