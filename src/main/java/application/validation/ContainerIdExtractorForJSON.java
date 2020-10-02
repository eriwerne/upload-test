package application.validation;

import application.output.formats.json.*;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ContainerIdExtractorForJSON implements ContainerIdExtractor {
    private final String json;

    public ContainerIdExtractorForJSON(String json) {
        this.json = json;
    }

    @Override
    public List<String> readContainerIdsInObject() {
        JsonNodeOrder jsonNodeOrder = new Gson().fromJson(String.valueOf(this.json), JsonNodeOrder.class);
        List<String> actualContainerIds = new ArrayList<>();
        for (JsonNodeImageGroup jsonNodeImageGroup : jsonNodeOrder.getImageGroups()) {
            addAllContainerIdsInMaterials(actualContainerIds, jsonNodeImageGroup.getBasisOcvModel().getMaterials());
            if (jsonNodeImageGroup.getFunctionModels() != null)
                for (JsonNodeFunctionModel jsonNodeFunctionModel : jsonNodeImageGroup.getFunctionModels())
                    addAllContainerIdsInMaterials(actualContainerIds, jsonNodeFunctionModel.getMaterials());
        }
        return actualContainerIds;
    }

    private void addAllContainerIdsInMaterials(List<String> actualContainerIds, List<JsonNodeMaterials> jsonNodeMaterials) {
        for (JsonNodeMaterials jsonNodeMaterial : jsonNodeMaterials) {
            for (JsonNodeImage jsonNodeImage : jsonNodeMaterial.getImages()) {
                if (jsonNodeImage.getFilenames() != null)
                    actualContainerIds.addAll(jsonNodeImage.getFilenames());
                if (jsonNodeImage.getFilenames_mirror() != null)
                    actualContainerIds.addAll(jsonNodeImage.getFilenames_mirror());
            }
        }
    }
}
