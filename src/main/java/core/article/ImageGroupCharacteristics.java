package core.article;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ImageGroupCharacteristics  implements Serializable {
    private final String styleId;
    private final List<String> height;
    private final List<String> depth;
    private final List<String> width;

    public ImageGroupCharacteristics(String styleId, List<String> height, List<String> depth, List<String> width) {
        this.styleId = styleId;
        this.height = height;
        this.depth = depth;
        this.width = width;
    }

    public String getStyleId() {
        return styleId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!ImageGroupCharacteristics.class.isAssignableFrom(obj.getClass()))
            return false;

        ImageGroupCharacteristics other = (ImageGroupCharacteristics) obj;
        return this.styleId.equals(other.styleId)
                && this.depth.equals(other.depth)
                && this.height.equals(other.height)
                && this.width.equals(other.width);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                styleId.hashCode(),
                height.hashCode(),
                width.hashCode(),
                depth.hashCode());
    }

    @Override
    public String toString() {
        return "ImageGroupCharacteristics{" +
                "styleId='" + styleId + '\'' +
                ", height=" + height +
                ", depth=" + depth +
                ", width=" + width +
                '}';
    }
}
