package application.output.formats.json;

import core.order.ImageGroupOrder;
import core.order.Order;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class JsonNodeOrder {
    private final String orderNumber;
    private final List<JsonNodeImageGroup> imageGroups;

    public JsonNodeOrder(Order order) {
        this.orderNumber = order.getOrderNumber();
        imageGroups = new ArrayList<>();
        for (ImageGroupOrder imageGroupOrder : order.getImageGroupOrders())
            imageGroups.add(new JsonNodeImageGroup(imageGroupOrder, order.getFilenameArticleRelations()));
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public List<JsonNodeImageGroup> getImageGroups() {
        return imageGroups;
    }
}
