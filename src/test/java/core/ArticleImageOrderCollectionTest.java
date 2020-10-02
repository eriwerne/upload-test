package core;

import core.article.Article;
import core.article.ImageGroupCharacteristics;
import core.article.Materials;
import org.junit.Before;
import org.junit.Test;
import utils.UnitTest;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArticleImageOrderCollectionTest extends UnitTest {

    private ArticleImageOrderCollection cut;

    @Before
    public void setup() {
        cut = new ArticleImageOrderCollection();
    }

    @Test
    public void when_styleids_are_requested_then_all_styleids_are_returned() {
        String styleId1 = fixtureString();
        String styleId2 = fixtureString();
        Article article2 = createArticleForStyle(styleId2);
        Article article1 = createArticleForStyle(styleId1);
        Article article3 = createArticleForStyle(styleId1);
        cut.addArticle(article1);
        cut.addArticle(article2);
        cut.addArticle(article3);

        assertEquals(2, cut.getStyleIds().size());
    }

    @Test
    public void when_materials_for_style_are_requested_then_materials_are_returned() {
        String styleId1 = fixtureString();
        String styleId2 = fixtureString();
        Materials materials1 = fixtureMaterials();
        Materials materials2 = fixtureMaterials();
        ImageGroupCharacteristics imageGroupCharacteristicsForStyle1 = createImageGroupCharacteristicsForStyle(styleId1);
        ImageGroupCharacteristics imageGroupCharacteristicsForStyle2 = createImageGroupCharacteristicsForStyle(styleId2);
        cut.addArticle(new Article(fixtureString(), materials1, null, false, "", imageGroupCharacteristicsForStyle1));
        cut.addArticle(new Article(fixtureString(), materials1, null, false, "", imageGroupCharacteristicsForStyle1));
        cut.addArticle(new Article(fixtureString(), materials2, null, false, "", imageGroupCharacteristicsForStyle1));
        cut.addArticle(new Article(fixtureString(), fixtureMaterials(), null, false, "", imageGroupCharacteristicsForStyle2));

        HashSet<Materials> act = cut.getMaterialsForImageGroupHash((imageGroupCharacteristicsForStyle1.hashCode()));
        assertEquals(2, act.size());
        assertTrue(act.contains(materials1));
        assertTrue(act.contains(materials2));
    }
}
