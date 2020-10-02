package infrastructure.m2.mapping;

import com.google.gson.Gson;
import core.article.Article;
import core.article.ImageGroupCharacteristics;
import core.article.Materials;
import core.article.MirrorInformation;
import core.article.cgi.CgiPolsterMoebelMirrorInformation;
import core.article.exceptions.InvalidArticleData;
import core.article.exceptions.UnknownMirrorInformation;
import infrastructure.m2.schema.M2ItemData;

import java.util.HashSet;

public class M2ArticleMapper {
    public Article mapFromJson(String json) throws RuntimeException, InvalidArticleData {
        M2ItemData data = new Gson().fromJson(json, M2ItemData[].class)[0];

        Materials materials = new Materials(data.getColorReference(), data.getFurnitureCoverMaterial(), data.getFurnitureCoverColor(), data.getFeetMaterial(), data.getFeetColor());
        HashSet<String> functionsHashSet = new HashSet<>(data.getArticleFunctions());
        boolean isMirror = createMirrorInformation(data).isMirror();

        ImageGroupCharacteristics imageGroupCharacteristics = new ImageGroupCharacteristics(
                data.getStyleId(),
                data.getHeight(),
                data.getDepth(),
                data.getWidth());
        return new Article(data.getArticleNumber(), materials, functionsHashSet, isMirror, data.getName(), imageGroupCharacteristics);
    }

    private MirrorInformation createMirrorInformation(M2ItemData data) throws UnknownMirrorInformation {
        String mirrorInformationString = extractMirrorInformationString(data);
        return new CgiPolsterMoebelMirrorInformation(mirrorInformationString);
    }

    private String extractMirrorInformationString(M2ItemData data) throws UnknownMirrorInformation {
        String[] variantMirrorInformation = data.getVariantMirrorInformation();
        if (variantMirrorInformation != null && variantMirrorInformation.length > 1)
            throw new UnknownMirrorInformation(variantMirrorInformation);

        String relevantString;
        if (variantMirrorInformation == null) {
            relevantString = data.getDimension3();
        } else {
            relevantString = variantMirrorInformation[0];
        }
        return relevantString;
    }
}
