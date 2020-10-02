package infrastructure.mmp.schema;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class MmpImageOrderContainerList {
    @SerializedName("ContainerId")
    private final String containerId;
    @SerializedName("Inhaltsklassifizierung")
    private final String inhaltsklassifizierung;
    @SerializedName("Ansicht")
    private final String ansicht;
    @SerializedName("Aufnahmeart")
    private final String aufnahmeart;
    @SerializedName("Detailfotografie")
    private final String detailfotografie;
    @SerializedName("KombinationsId")
    private final String kombinationsId;


    public MmpImageOrderContainerList(String containerId, String inhaltsklassifizierung, String ansicht, String aufnahmeart, String detailfotografie, String kombinationsId) {
        this.containerId = containerId;
        this.inhaltsklassifizierung = inhaltsklassifizierung;
        this.ansicht = ansicht;
        this.aufnahmeart = aufnahmeart;
        this.detailfotografie = detailfotografie;
        this.kombinationsId = kombinationsId;
    }

    public String getContainerId() {
        return containerId;
    }

    public String getInhaltsklassifizierung() {
        return inhaltsklassifizierung;
    }

    public String getAnsicht() {
        return ansicht;
    }

    public String getAufnahmeart() {
        return aufnahmeart;
    }

    public String getDetailfotografie() {
        return detailfotografie;
    }

    public String getKombinationsId() {
        return kombinationsId;
    }
}
