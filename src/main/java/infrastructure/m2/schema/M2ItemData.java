package infrastructure.m2.schema;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class M2ItemData {
    private final String articleNumber;
    private final M2LooseCharacteristics looseCharacteristics;
    private final String colorReference;
    private final String dimension3;
    @SerializedName("@links")
    private final M2Links links;
    private final String name;
    private final String orderName;

    public M2ItemData(String articleNumber,
                      M2LooseCharacteristics looseCharacteristics,
                      String colorReference,
                      String dimension3,
                      String dimension4,
                      M2Links links,
                      String name,
                      String orderName) {
        this.articleNumber = articleNumber;
        this.looseCharacteristics = looseCharacteristics;
        this.colorReference = colorReference;
        this.dimension3 = dimension3;
        this.links = links;
        this.name = name;
        this.orderName = orderName;
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public String[] getFurnitureCoverMaterial() {
        return looseCharacteristics.getFurnitureCoverMaterial();
    }

    public String[] getFeetColor() {
        return looseCharacteristics.getFeetColor();
    }

    public String[] getFeetMaterial() {
        return looseCharacteristics.getFeetMaterial();
    }

    public String[] getFurnitureCoverColor() {
        return looseCharacteristics.getFurnitureCoverColor();
    }

    public String[] getVariantMirrorInformation() {
        return looseCharacteristics.getVariantMirrorInformation();
    }

    public List<String> getArticleFunctions() {
        ArrayList<String> functions = new ArrayList<>();
        if (looseCharacteristics.getAusstattung() != null)
            functions.addAll(Arrays.asList(looseCharacteristics.getAusstattung()));
        if (looseCharacteristics.getAusfuehrung() != null)
            functions.addAll(Arrays.asList(looseCharacteristics.getAusfuehrung()));
        return functions;
    }

    public String getStyleId() {
        return links.getRoot().toString().replace("https://api.prod.m2.ov.otto.de/entity/style/", "").split("/")[0];
    }

    public String getDimension3() {
        return dimension3;
    }

    public String getColorReference() {
        return colorReference;
    }

    public ArrayList<String> getDepth() {
        ArrayList<String> depth = new ArrayList<>();
        if (looseCharacteristics.getDepth() != null)
            depth.addAll(Arrays.asList(looseCharacteristics.getDepth()));
        if (looseCharacteristics.getDepthSeat() != null)
            depth.addAll(Arrays.asList(looseCharacteristics.getDepthSeat()));
        return depth;
    }

    public ArrayList<String> getWidth() {
        ArrayList<String> width = new ArrayList<>();
        if (looseCharacteristics.getWidth() != null)
            width.addAll(Arrays.asList(looseCharacteristics.getWidth()));
        if (looseCharacteristics.getWidthSeat() != null)
            width.addAll(Arrays.asList(looseCharacteristics.getWidthSeat()));
        return width;
    }

    public ArrayList<String> getHeight() {
        ArrayList<String> height = new ArrayList<>();
        if (looseCharacteristics.getHeight() != null)
            height.addAll(Arrays.asList(looseCharacteristics.getHeight()));
        if (looseCharacteristics.getHeightSeat() != null)
            height.addAll(Arrays.asList(looseCharacteristics.getHeightSeat()));
        return height;
    }

    public String getName() {
        return name + " (" + orderName + ")";
    }
}
