package core.image;

import java.util.ArrayList;
import java.util.Collections;

public class ImageOrder implements java.io.Serializable{
    private final String perspective;
    private final ArrayList<String> filenames = new ArrayList<>();
    private final ArrayList<String> filenamesMirror = new ArrayList<>();
    private final String inhaltsklassifizierung;
    private final String aufnahmeart;
    private final DetailFotografie detailfotografie;
    private int idCounter = 0;

    public String getPerspective() {
        return perspective;
    }

    public String getInhaltsklassifizierung() {
        return inhaltsklassifizierung;
    }

    public String getAufnahmeart() {
        return aufnahmeart;
    }

    public DetailFotografie getDetailfotografie() {
        return detailfotografie;
    }

    public ImageOrder(String perspective, ArrayList<String> filenames, String inhaltsklassifizierung, String aufnahmeart, DetailFotografie detailfotografie) {
        this.perspective = (perspective == null ? "" : perspective);
        this.inhaltsklassifizierung = (inhaltsklassifizierung == null ? "" : inhaltsklassifizierung);
        this.aufnahmeart = (aufnahmeart == null ? "" : aufnahmeart);
        this.detailfotografie = (detailfotografie == null ? DetailFotografie.NULL : detailfotografie);

        this.filenames.addAll(filenames);
    }

    public Boolean isFunctionImage() {
        return detailfotografie == DetailFotografie.FUNKTION;
    }

    public ArrayList<String> getFilenames() {
        Collections.sort(filenames);
        return filenames;
    }

    public ArrayList<String> getFilenamesMirror() {
        Collections.sort(filenamesMirror);
        return filenamesMirror;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!ImageOrder.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        ImageOrder other = (ImageOrder) obj;

        return this.detailfotografie.equals(other.detailfotografie)
                && this.aufnahmeart.equals(other.aufnahmeart)
                && this.inhaltsklassifizierung.equals(other.inhaltsklassifizierung)
                && this.perspective.equals(other.perspective)
                && this.idCounter == other.idCounter;
    }

    public void addDuplicateFilenames(ImageOrder duplicateImageOrder) {
        for (String filename : duplicateImageOrder.filenames)
            if (!filenames.contains(filename))
                filenames.add(filename);
        for (String filenameMirror : duplicateImageOrder.filenamesMirror)
            if (!filenamesMirror.contains(filenameMirror))
                filenamesMirror.add(filenameMirror);
    }

    public void increaseIdCounter() {
        idCounter++;
    }
}
