package core.article;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Article implements Serializable {
    private final String articleNumber;
    private final HashSet<String> articleFunctions = new HashSet<>();
    private final Boolean isMirror;
    private final Materials materials;
    private final String name;
    private final ImageGroupCharacteristics imageGroupCharacteristics;

    public Article(String articleNumber, Materials materials, HashSet<String> articleFunctions, boolean isMirror, String name, ImageGroupCharacteristics imageGroupCharacteristics) {
        this.articleNumber = articleNumber;
        this.isMirror = isMirror;
        this.materials = materials;
        this.imageGroupCharacteristics = imageGroupCharacteristics;
        this.name = name;

        setFunctions(articleFunctions);
    }

    private void setFunctions(HashSet<String> articleFunctions) {
        if (articleFunctions == null)
            return;

        List<String> functions = new ArrayList<>();
        for (String function : articleFunctions) {
            functions.addAll(Arrays.asList(function.replaceAll(", ", ",").split(",")));
        }
        if (functionsAreValid(functions))
            for (String function : functions) {
                String functionOutput = function.replaceAll("mit ", "").replaceAll("Mit ", "");
                this.articleFunctions.add(functionOutput);
            }
    }

    private boolean functionsAreValid(List<String> articleFunctions) {
        if (articleFunctions == null)
            return false;
        for (String function : articleFunctions)
            if (!function.matches("([oO])hne \\S*( und \\S*)?"))
                return true;
        return false;
    }

    public HashSet<String> getArticleFunctions() {
        return articleFunctions;
    }

    public String getArticleFunctionDescription() {
        if (articleFunctions.size() == 1) {
            for (String function : articleFunctions)
                return function;
        } else if (articleFunctions.size() == 0) {
            return "";
        }
        return articleFunctions.toString();
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public String getStyleId() {
        return imageGroupCharacteristics.getStyleId();
    }

    public Boolean isMirror() {
        return isMirror;
    }

    public boolean hasFunction() {
        return articleFunctions.size() > 0;
    }

    public Materials getMaterials() {
        return materials;
    }

    public boolean isInImageGroupWith(Article other) {
        return this.imageGroupCharacteristics.equals(other.imageGroupCharacteristics);
    }

    public String getName() {
        return name;
    }

    public ImageGroupCharacteristics getImageGroup() {
        return imageGroupCharacteristics;
    }

    @Override
    public String toString() {
        return "\nArticle{" +
                "\n\tarticleNumber='" + articleNumber + '\'' +
                ", \n\tarticleFunctions=" + articleFunctions +
                ", \n\tisMirror=" + isMirror +
                ", \n\tmaterials=" + materials +
                ", \n\tname='" + name + '\'' +
                ", \n\timageGroupCharacteristics=" + imageGroupCharacteristics +
                "\n}";
    }
}
