package infrastructure.m2.schema;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
class M2LooseCharacteristics {

    @SerializedName("Bezug")
    private final String[] furnitureCoverMaterial;
    @SerializedName("Farbe")
    private final String[] furnitureCoverColor;
    @SerializedName("Material Füße")
    private final String[] feetMaterial;
    @SerializedName("Farbe Füße")
    private final String[] feetColor;
    @SerializedName("Stellvariante")
    private final String[] variantMirrorInformation;
    @SerializedName("Ausführung")
    private final String[] ausfuehrung;
    @SerializedName("Ausstattung")
    private final String[] ausstattung;
    @SerializedName("Tiefe")
    private final String[] depth;
    @SerializedName("Breite")
    private final String[] width;
    @SerializedName("Höhe")
    private final String[] height;
    @SerializedName("Tiefe Sitzfläche")
    private final String[] depthSeat;
    @SerializedName("Breite Sitzfläche")
    private final String[] widthSeat;
    @SerializedName("Sitzhöhe")
    private final String[] heightSeat;

    public M2LooseCharacteristics(String[] furnitureCoverMaterial,
                                  String[] furnitureCoverColor,
                                  String[] feetMaterial,
                                  String[] feetColor,
                                  String[] variantMirrorInformation,
                                  String[] ausfuehrung,
                                  String[] ausstattung,
                                  String[] depth,
                                  String[] width,
                                  String[] height,
                                  String[] depthSeat,
                                  String[] widthSeat,
                                  String[] heightSeat) {
        this.furnitureCoverMaterial = furnitureCoverMaterial;
        this.furnitureCoverColor = furnitureCoverColor;
        this.feetMaterial = feetMaterial;
        this.feetColor = feetColor;
        this.variantMirrorInformation = variantMirrorInformation;
        this.depth = depth;
        this.width = width;
        this.height = height;
        this.depthSeat = depthSeat;
        this.widthSeat = widthSeat;
        this.heightSeat = heightSeat;
        this.ausfuehrung = ausfuehrung;
        this.ausstattung = ausstattung;
    }

    public String[] getFurnitureCoverMaterial() {
        return furnitureCoverMaterial;
    }

    public String[] getFeetColor() {
        return feetColor;
    }

    public String[] getFeetMaterial() {
        return feetMaterial;
    }

    public String[] getFurnitureCoverColor() {
        return furnitureCoverColor;
    }

    public String[] getVariantMirrorInformation() {
        return variantMirrorInformation;
    }

    public String[] getAusfuehrung() {
        return ausfuehrung;
    }

    public String[] getAusstattung() {
        return ausstattung;
    }

    public String[] getDepthSeat() {
        return depthSeat;
    }

    public String[] getWidthSeat() {
        return widthSeat;
    }

    public String[] getHeightSeat() {
        return heightSeat;
    }

    public String[] getDepth() {
        return depth;
    }

    public String[] getWidth() {
        return width;
    }

    public String[] getHeight() {
        return height;
    }
}
