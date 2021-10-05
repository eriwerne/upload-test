package core.order;

import core.article.Materials;
import core.image.ImageOrder;
import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.util.*;

public class CgiModel implements Serializable {
    private final ImageOrdersByMaterialAndFunction materialsImageOrderListMap = new ImageOrdersByMaterialAndFunction();

    public ImageOrdersByMaterialAndFunction getMaterialsImageOrderListMap() {
        return sortList(materialsImageOrderListMap);
    }

    public void appendImageOrdersToMaterials(ImageOrder imageOrder, Materials materials, String function) {
        if (!materialsImageOrderListMap.containsKey(materials, function)) {
            materialsImageOrderListMap.put(materials, function, new ArrayList<>());
        }
        List<ImageOrder> materialsImageOrders = materialsImageOrderListMap.get(materials, function);
        if (!materialsImageOrders.contains(imageOrder))
            materialsImageOrders.add(imageOrder);
    }

    private ImageOrdersByMaterialAndFunction sortList(ImageOrdersByMaterialAndFunction materialsImageOrderListMap) {
        ArrayList<ImageOrderGroupKey> sortedMaterials = new ArrayList<>(materialsImageOrderListMap.keySet());
        sortedMaterials.sort(Comparator.comparing(it -> it.materials.sortValue()));

        ImageOrdersByMaterialAndFunction sortedList = new ImageOrdersByMaterialAndFunction();
        for (ImageOrderGroupKey material : sortedMaterials)
            sortedList.put(material.materials, material.function, materialsImageOrderListMap.get(material));
        return sortedList;
    }
}
