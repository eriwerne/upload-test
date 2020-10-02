package application.output.formats.json;

import core.article.Article;
import core.article.Materials;
import core.order.CgiModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonNodeBasicModel {
    private final List<JsonNodeMaterials> materials;

    public JsonNodeBasicModel(CgiModel basicModel, HashMap<String, Article> filenameArticleRelations) {
        materials = new ArrayList<>();
        for (Materials materials : basicModel.getMaterialsImageOrderListMap().keySet()) {
            this.materials.add(new JsonNodeMaterials(basicModel.getMaterialsImageOrderListMap().get(materials), materials, filenameArticleRelations));
        }
    }

    public List<JsonNodeMaterials> getMaterials() {
        return materials;
    }
}
