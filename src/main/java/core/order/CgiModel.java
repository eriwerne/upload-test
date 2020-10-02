package core.order;

import core.article.Materials;
import core.image.ImageOrder;

import java.io.Serializable;
import java.util.*;

public class CgiModel  implements Serializable {
    private final LinkedHashMap<Materials, List<ImageOrder>> materialsImageOrderListMap = new LinkedHashMap<>();

    public LinkedHashMap<Materials, List<ImageOrder>> getMaterialsImageOrderListMap() {
        return sortList(materialsImageOrderListMap);
    }

    public void appendImageOrdersToMaterials(ImageOrder imageOrder, Materials materials) {
        if (!materialsImageOrderListMap.containsKey(materials)) {
            materialsImageOrderListMap.put(materials, new ArrayList<>());
        }
        List<ImageOrder> materialsImageOrders = materialsImageOrderListMap.get(materials);
        if (!materialsImageOrders.contains(imageOrder))
            materialsImageOrders.add(imageOrder);
    }

    private LinkedHashMap<Materials, List<ImageOrder>> sortList(LinkedHashMap<Materials, List<ImageOrder>> materialsImageOrderListMap) {
        ArrayList<Materials> sortedMaterials = new ArrayList<>(materialsImageOrderListMap.keySet());
        sortedMaterials.sort(Comparator.comparing(Materials::sortValue));

        LinkedHashMap<Materials, List<ImageOrder>> sortedList = new LinkedHashMap<>();
        for (Materials material : sortedMaterials)
            sortedList.put(material, materialsImageOrderListMap.get(material));
        return sortedList;
    }
}
