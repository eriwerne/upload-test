package application.output.formats.json;

import core.article.Article;
import core.order.ImageGroupOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unused")
public class JsonNodeImageGroup {
    private final String productComKey;
    private final JsonNodeBasicModel basisOcvModel;
    private final List<JsonNodeFunctionModel> functionModels;


    public JsonNodeImageGroup(ImageGroupOrder imageGroupOrder, HashMap<String, Article> filenameArticleRelations) {
        productComKey = imageGroupOrder.getStyleId();
        basisOcvModel = new JsonNodeBasicModel(imageGroupOrder.getBasicModel(), filenameArticleRelations);
        functionModels = new ArrayList<>();
        for (String function : imageGroupOrder.getFunctions())
            functionModels.add(new JsonNodeFunctionModel(function, imageGroupOrder.getFunctionModel(function), filenameArticleRelations));
    }

    public String getProductComKey() {
        return productComKey;
    }

    public JsonNodeBasicModel getBasisOcvModel() {
        return basisOcvModel;
    }

    public List<JsonNodeFunctionModel> getFunctionModels() {
        return functionModels;
    }
}
