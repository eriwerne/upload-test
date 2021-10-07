package core.article;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Materials implements Serializable {
    private String materialCode = null;
    private final String[] furnitureCoverMaterial;
    private final String[] furnitureCoverColor;
    private final String[] feetMaterial;
    private final String[] feetColor;

    public Materials(String materialCode, String[] furnitureCoverMaterial, String[] furnitureCoverColor, String[] feetMaterial, String[] feetColor) {
        this.furnitureCoverMaterial = furnitureCoverMaterial;
        this.furnitureCoverColor = furnitureCoverColor;
        this.feetMaterial = feetMaterial == null ? new String[]{} : feetMaterial;
        this.feetColor = feetColor == null ? new String[]{} : feetColor;
        if (materialCode != null)
            this.materialCode = materialCode.replaceAll(";", ",");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!Materials.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        return hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        if (materialCode != null)
            return Objects.hash(
                    materialCode.hashCode(),
                    Arrays.hashCode(furnitureCoverMaterial),
                    Arrays.hashCode(furnitureCoverColor),
                    Arrays.hashCode(feetMaterial),
                    Arrays.hashCode(feetColor));
        else
            return Objects.hash(
                    Arrays.hashCode(furnitureCoverMaterial),
                    Arrays.hashCode(furnitureCoverColor),
                    Arrays.hashCode(feetMaterial),
                    Arrays.hashCode(feetColor));

    }


    public String getMaterialCode() {
        return materialCode;
    }

    public String[] getFurnitureCoverMaterial() {
        return furnitureCoverMaterial;
    }

    public String[] getFurnitureCoverColor() {
        return furnitureCoverColor;
    }

    public String[] getFeetMaterial() {
        return feetMaterial;
    }

    public String[] getFeetColor() {
        return feetColor;
    }


    public String sortValue() {
        StringBuilder sortValue = new StringBuilder();
        appendSortAttribute(sortValue, furnitureCoverColor);
        appendSortAttribute(sortValue, furnitureCoverMaterial);
        appendSortAttribute(sortValue, feetColor);
        appendSortAttribute(sortValue, feetMaterial);
        sortValue.append(materialCode);
        return sortValue.toString();
    }

    private void appendSortAttribute(StringBuilder sortValue, String[] attribute) {
        if (attribute != null)
            for (String s : attribute)
                sortValue.append(s);
    }

    @Override
    public String toString() {
        return "Materials{" +
                "materialCode='" + materialCode + '\'' +
                ", furnitureCoverMaterial=" + Arrays.toString(furnitureCoverMaterial) +
                ", furnitureCoverColor=" + Arrays.toString(furnitureCoverColor) +
                ", feetMaterial=" + Arrays.toString(feetMaterial) +
                ", feetColor=" + Arrays.toString(feetColor) +
                '}';
    }
}
