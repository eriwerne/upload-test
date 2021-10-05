package core.order;

import core.article.Materials;

import java.util.Objects;

public class ImageOrderGroupKey {
    public Materials materials;
    public String function;

    public ImageOrderGroupKey(Materials materials, String function) {
        this.materials = materials;
        this.function = function;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageOrderGroupKey that = (ImageOrderGroupKey) o;
        return Objects.equals(materials, that.materials) &&
                Objects.equals(function, that.function);
    }

    @Override
    public int hashCode() {
        return Objects.hash(materials, function);
    }
}
