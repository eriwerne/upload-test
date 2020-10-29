package application.output.formats.json;

import core.image.DetailFotografie;
import core.image.ImageOrder;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class JsonNodeImage {
    private final String detailfotografie;
    private final String ansicht;
    private final String aufnahmeart;
    private final List<String> recamiereOttomaneLinks;
    private final List<String> recamiereOttomaneRechts;

    public String getDetailfotografie() {
        return detailfotografie;
    }

    public String getAnsicht() {
        return ansicht;
    }

    public String getAufnahmeart() {
        return aufnahmeart;
    }


    public List<String> getRecamiereOttomaneLinks() {
        return recamiereOttomaneLinks;
    }

    public List<String> getRecamiereOttomaneRechts() {
        return recamiereOttomaneRechts;
    }

    public JsonNodeImage(ImageOrder imageOrder) {
        ansicht = imageOrder.getPerspective();
        aufnahmeart = imageOrder.getAufnahmeart();
        DetailFotografie imagedetailfotografie = imageOrder.getDetailfotografie();
        detailfotografie = imagedetailfotografie.toString();
        recamiereOttomaneLinks = new ArrayList<>();
        if (imageOrder.getFilenames().size() > 0) {
            this.recamiereOttomaneLinks.addAll(imageOrder.getFilenames());
        }
        recamiereOttomaneRechts = new ArrayList<>();
        if (imageOrder.getFilenamesMirror().size() > 0) {
            recamiereOttomaneRechts.addAll(imageOrder.getFilenamesMirror());
        }
    }
}
