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
    private final List<String> filenames;
    private final List<String> filenames_mirror;

    public String getDetailfotografie() {
        return detailfotografie;
    }

    public String getAnsicht() {
        return ansicht;
    }

    public String getAufnahmeart() {
        return aufnahmeart;
    }


    public List<String> getFilenames() {
        return filenames;
    }

    public List<String> getFilenames_mirror() {
        return filenames_mirror;
    }

    public JsonNodeImage(ImageOrder imageOrder) {
        ansicht = imageOrder.getPerspective();
        aufnahmeart = imageOrder.getAufnahmeart();
        DetailFotografie imagedetailfotografie = imageOrder.getDetailfotografie();
        detailfotografie = imagedetailfotografie.toString();
        filenames = new ArrayList<>();
        if (imageOrder.getFilenames().size() > 0) {
            this.filenames.addAll(imageOrder.getFilenames());
        }
        filenames_mirror = new ArrayList<>();
        if (imageOrder.getFilenamesMirror().size() > 0) {
            filenames_mirror.addAll(imageOrder.getFilenamesMirror());
        }
    }
}
