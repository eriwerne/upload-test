package infrastructure.mmp.mapping;

import com.google.gson.Gson;
import core.image.DetailFotografie;
import core.image.ImageOrder;
import infrastructure.mmp.schema.MmpImageOrderArticle;
import infrastructure.mmp.schema.MmpImageOrderContainerList;
import infrastructure.mmp.schema.MmpOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static core.image.DetailFotografie.*;

public class MmpImageOrderMapper {
    public HashMap<String, List<ImageOrder>> mapFromMmpObject(MmpOrder mmpOrder) {
        HashMap<String, List<ImageOrder>> articleImageOrders = new HashMap<>();
        for (MmpImageOrderArticle mmpArticle : mmpOrder.getArticleList()) {
            ArrayList<ImageOrder> imageOrders = new ArrayList<>();
            for (MmpImageOrderContainerList mmpContainer : mmpArticle.getContainerList()) {
                if (mmpContainer.getKombinationsId() != null)
                    continue;

                ArrayList<String> filenames = new ArrayList<>();
                filenames.add(mmpContainer.getContainerId() + ".jpg");
                String ansicht = mmpContainer.getAnsicht();
                String inhaltsklassifizierung = mmpContainer.getInhaltsklassifizierung();
                String aufnahmeart = mmpContainer.getAufnahmeart();
                DetailFotografie detailfotografieFromString = getDetailfotografieFromString(mmpContainer.getDetailfotografie());

                ImageOrder imageOrder = new ImageOrder(
                        ansicht,
                        filenames,
                        inhaltsklassifizierung,
                        aufnahmeart,
                        detailfotografieFromString);
                imageOrders.add(imageOrder);
            }
            if (!imageOrders.isEmpty())
                articleImageOrders.put(mmpArticle.getArticleNumber(), imageOrders);
        }

        return articleImageOrders;
    }

    private DetailFotografie getDetailfotografieFromString(String string) {
        if (string == null)
            return NULL;
        switch (string) {
            case "Digitales Stoffmuster":
                return DIGITALESSTOFFMUSTER;
            case "Funktion":
                return FUNKTION;
            case "Sortimentsdetail":
                return SORTIMENTSDETAIL;
            case "Zubehör":
                return ZUBEHOER;
            case "Farbfleck":
                return FARBFLECK;
            case "Markendetail":
                return MARKENDETAIL;
            case "Material / Stoff":
                return MATERIALSTOFF;
            case "Materialmuster":
                return MATERIALMUSTER;
            default:
                throw new IllegalStateException("Unexpected value: " + string);
        }
    }

    public MmpOrder mapJsonToMmpObject(String json) {
        return new Gson().fromJson(json, MmpOrder.class);
    }
}
