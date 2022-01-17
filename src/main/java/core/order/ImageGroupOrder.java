package core.order;

import core.article.Materials;
import core.image.ImageOrder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ImageGroupOrder  implements Serializable {
    private final String syleId;
    private final CgiModel basicModelInstructions = new CgiModel();
    private final HashMap<String, CgiModel> functionModelInstructionsMap = new HashMap<>();
    private final String sizes;

    public ImageGroupOrder(String syleId, String sizes) {
        this.syleId = syleId;
        this.sizes = sizes;
    }

    public void appendImageOrders(Materials materials, String function, List<ImageOrder> imageOrders) {
        for (ImageOrder imageOrder : imageOrders) {
            CgiModel relevantModel;
            if (imageOrder.isFunctionImage()) {
                relevantModel = getCgiModelForFunction(function);
            } else {
                relevantModel = basicModelInstructions;
            }
            relevantModel.appendImageOrdersToMaterials(imageOrder, materials, function);
        }
    }

    private CgiModel getCgiModelForFunction(String function) {
        CgiModel functionModelInstructions;
        if (functionModelInstructionsMap.containsKey(function)) {
            functionModelInstructions = functionModelInstructionsMap.get(function);
        } else {
            functionModelInstructions = new CgiModel();
            functionModelInstructionsMap.put(function, functionModelInstructions);
        }
        return functionModelInstructions;
    }

    public CgiModel getFunctionModel(String function) {
        return functionModelInstructionsMap.get(function);
    }

    public CgiModel getBasicModel() {
        return basicModelInstructions;
    }

    public Set<String> getFunctions() {
        return functionModelInstructionsMap.keySet();
    }

    public String getStyleId() {
        return syleId;
    }

    public String getSizes() {
        return sizes;
    }
}
