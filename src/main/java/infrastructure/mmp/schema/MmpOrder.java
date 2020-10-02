package infrastructure.mmp.schema;

import com.google.gson.annotations.SerializedName;

public class MmpOrder {
    @SerializedName("Artikel")
    private final MmpImageOrderArticle[] articleList;

    public MmpOrder(MmpImageOrderArticle[] articleList) {
        this.articleList = articleList;
    }

    public MmpImageOrderArticle[] getArticleList() {
        return articleList;
    }
}
