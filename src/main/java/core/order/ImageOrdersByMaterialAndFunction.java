package core.order;

import core.article.Materials;
import core.image.ImageOrder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class ImageOrdersByMaterialAndFunction {
    public final LinkedHashMap<ImageOrderGroupKey, List<ImageOrder>> imageOrderGroups = new LinkedHashMap<>();

    public Set<ImageOrderGroupKey> keySet() {
        return imageOrderGroups.keySet();
    }

    public List<ImageOrder> get(Materials materials, String function) {
        return imageOrderGroups.get(new ImageOrderGroupKey(materials, function));
    }

    public List<ImageOrder> get(ImageOrderGroupKey key) {
        return imageOrderGroups.get(key);
    }

    public boolean containsKey(Materials materials, String function) {
        return imageOrderGroups.containsKey(new ImageOrderGroupKey(materials, function));
    }

    public void put(Materials materials, String function, List<ImageOrder> elements) {
        imageOrderGroups.put(new ImageOrderGroupKey(materials, function), elements);
    }
}
