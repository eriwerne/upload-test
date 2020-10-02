package application.validation;

import core.article.Materials;
import core.image.ImageOrder;
import core.order.CgiModel;
import core.order.ImageGroupOrder;
import core.order.Order;

import java.util.ArrayList;
import java.util.List;

public class ContainerIdExtractorForOrderObject implements ContainerIdExtractor {
    private final Order order;

    public ContainerIdExtractorForOrderObject(Order order) {
        this.order = order;
    }

    @Override
    public List<String> readContainerIdsInObject() {
        ArrayList<String> containerIds = new ArrayList<>();
        for (ImageGroupOrder imageGroupOrder : order.getImageGroupOrders()) {
            containerIds.addAll(handleModel(imageGroupOrder.getBasicModel()));
            for (String function : imageGroupOrder.getFunctions())
                containerIds.addAll(handleModel(imageGroupOrder.getFunctionModel(function)));
        }
        return containerIds;
    }

    private List<String> handleModel(CgiModel model) {
        List<String> containerIds = new ArrayList<>();
        for (Materials material : model.getMaterialsImageOrderListMap().keySet()) {
            for (ImageOrder imageOrder : model.getMaterialsImageOrderListMap().get(material)) {
                containerIds.addAll(imageOrder.getFilenames());
                containerIds.addAll(imageOrder.getFilenamesMirror());
            }
        }
        return containerIds;
    }
}
