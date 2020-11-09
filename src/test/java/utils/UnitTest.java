package utils;

import com.flextrade.jfixture.JFixture;
import com.flextrade.jfixture.annotations.Fixture;
import core.article.Article;
import core.article.ImageGroupCharacteristics;
import core.article.Materials;
import core.image.ImageOrder;

import java.util.ArrayList;
import java.util.HashSet;

public class UnitTest {
    private final JFixture fixture = new JFixture();

    protected ArrayList<String> listOfFilename(String filename) {
        ArrayList<String> filenameList = new ArrayList<>();
        filenameList.add(filename);
        return filenameList;
    }

    protected String fixtureString() {
        return fixture.create(String.class);
    }

    protected ImageOrder fixtureImageOrder() {
        return new ImageOrder(fixtureString(), fixtureList(), fixtureString(), fixtureString(), null);
    }

    protected ArrayList<String> fixtureList() {
        ArrayList<String> filenameList = new ArrayList<>();
        filenameList.add(fixtureString());
        return filenameList;
    }

    protected ImageGroupCharacteristics fixtureImageGroupCharacteristics() {
        return new ImageGroupCharacteristics(fixtureString(), fixtureList(), fixtureList(), fixtureList());
    }

    protected Materials fixtureMaterials() {
        return new Materials(
                fixtureString(),
                new String[]{fixtureString()},
                new String[]{fixtureString()},
                new String[]{fixtureString()},
                new String[]{fixtureString()}
        );
    }

    protected Article createArticleForArticleNumber(String articleNumber) {
        return new Article(articleNumber, fixtureMaterials(), null, false, "", fixtureImageGroupCharacteristics());
    }

    protected Article createArticleForImageGroupCharacteristics(ImageGroupCharacteristics imageGroupCharacteristics) {
        return new Article(fixtureString(), fixtureMaterials(), null, false, "", imageGroupCharacteristics);
    }

    protected Article createArticleForStyle(String styleA) {
        return createArticleForImageGroupCharacteristics(createImageGroupCharacteristicsForStyle(styleA));
    }

    protected Article createArticleForFunction(HashSet<String> functionListA) {
        return new Article(fixtureString(), fixtureMaterials(), functionListA, false, "", fixtureImageGroupCharacteristics());
    }

    protected ImageGroupCharacteristics createImageGroupCharacteristicsForStyle(String styleId) {
        return new ImageGroupCharacteristics(styleId, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }
}
