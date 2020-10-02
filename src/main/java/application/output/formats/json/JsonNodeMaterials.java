package application.output.formats.json;

import core.article.Article;
import core.article.Materials;
import core.image.ImageOrder;

import java.util.*;

@SuppressWarnings("unused")
public class JsonNodeMaterials {
    private final String materialCode;
    private final List<JsonNodeMaterialAttributes> materialAttributes = new ArrayList<>();
    private final List<JsonNodeArticle> articles;
    private final List<JsonNodeImage> images = new ArrayList<>();

    public JsonNodeMaterials(List<ImageOrder> imageOrders, Materials materials, HashMap<String, Article> filenameArticleRelations) {
        materialAttributes.add(new JsonNodeMaterialAttributes("materialCode", materials.getMaterialCode()));
        materialAttributes.add(new JsonNodeMaterialAttributes("furnitureCoverMaterial", Arrays.toString(materials.getFurnitureCoverMaterial())));
        materialAttributes.add(new JsonNodeMaterialAttributes("furnitureCoverColor", Arrays.toString(materials.getFurnitureCoverColor())));
        materialAttributes.add(new JsonNodeMaterialAttributes("feetMaterial", Arrays.toString(materials.getFeetMaterial())));
        materialAttributes.add(new JsonNodeMaterialAttributes("feetColor", Arrays.toString(materials.getFeetColor())));

        materialCode = materials.getMaterialCode();

        for (ImageOrder imageOrder : imageOrders)
            this.images.add(new JsonNodeImage(imageOrder));
        articles = new ArrayList<>();
        for (String filename : imageOrders.get(0).getFilenames()) {
            Article article = filenameArticleRelations.get(filename);
            JsonNodeArticle jsonNodeArticle = new JsonNodeArticle(article.getArticleNumber(), article.getName());
            if (!articles.contains(jsonNodeArticle))
                articles.add(jsonNodeArticle);
        }
        for (String filename : imageOrders.get(0).getFilenamesMirror()) {
            Article article = filenameArticleRelations.get(filename);
            JsonNodeArticle jsonNodeArticle = new JsonNodeArticle(article.getArticleNumber(), article.getName());
            if (!articles.contains(jsonNodeArticle))
                articles.add(jsonNodeArticle);
        }
    }

    public List<JsonNodeImage> getImages() {
        return images;
    }

    public List<JsonNodeMaterialAttributes> getMaterialAttributes() {
        return materialAttributes;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public List<JsonNodeArticle> getArticles() {
        articles.sort(Comparator.comparing(JsonNodeArticle::getArticleNumber));
        return articles;
    }
}
