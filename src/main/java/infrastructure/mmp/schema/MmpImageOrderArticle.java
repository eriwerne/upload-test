package infrastructure.mmp.schema;

import com.google.gson.annotations.SerializedName;

public class MmpImageOrderArticle {
    @SerializedName("Artikelnummer")
    private final String articleNumber;
    @SerializedName("Container")
    private final MmpImageOrderContainerList[] containerList;

    @SuppressWarnings("unused")
    public MmpImageOrderArticle(String articleNumber, MmpImageOrderContainerList[] containerList) {
        this.articleNumber = articleNumber;
        this.containerList = containerList;
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public MmpImageOrderContainerList[] getContainerList() {
        return containerList;
    }
}
