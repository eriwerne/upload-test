package application.output.formats.json;

import core.article.Article;
import core.article.Materials;
import core.order.CgiModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonNodeFunctionModel {
    private final String function;
    private final List<JsonNodeMaterials> materials;

    public JsonNodeFunctionModel(String function, CgiModel model, HashMap<String, Article> filenameArticleRelations) {
        this.function = function;
        materials = new ArrayList<>();
        for (Materials materials : model.getMaterialsImageOrderListMap().keySet()) {
            this.materials.add(new JsonNodeMaterials(model.getMaterialsImageOrderListMap().get(materials), materials, filenameArticleRelations));
        }
    }

    @SuppressWarnings("unused")
    public String getFunction() {
        return function;
    }

    public List<JsonNodeMaterials> getMaterials() {
        return materials;
    }

}
