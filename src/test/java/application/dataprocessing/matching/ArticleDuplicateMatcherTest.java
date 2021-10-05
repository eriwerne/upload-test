package application.dataprocessing.matching;

import core.article.Article;
import core.article.ImageGroupCharacteristics;
import core.article.Materials;
import org.junit.Before;
import org.junit.Test;
import utils.UnitTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

public class ArticleDuplicateMatcherTest extends UnitTest {
    private ArticleDuplicateMatcher cut;
    private List<Article> articles;

    @Before
    public void setup() {
        cut = new ArticleDuplicateMatcher();
        articles = new ArrayList<>();
    }

    @Test
    public void when_duplicate_matcher_is_called_with_empty_collection_then_not_null_is_returned() {
        List<List<String>> act = cut.matchDuplicatesGroups(new ArrayList<>());
        assertNotNull(act);
    }

    @Test
    public void when_duplicate_matcher_is_called_for_one_article_then_one_group_with_one_article_is_returned() {
        articles.add(createArticleForArticleNumber(fixtureString()));
        List<List<String>> act = cut.matchDuplicatesGroups(articles);
        assertEquals(1, act.size());
        assertEquals(1, act.get(0).size());
    }

    @Test
    public void when_duplicate_matcher_is_called_for_two_different_articles_then_two_groups_with_each_one_article_is_returned() {
        articles.add(createArticleForArticleNumber(fixtureString()));
        articles.add(createArticleForArticleNumber(fixtureString()));

        List<List<String>> act = cut.matchDuplicatesGroups(articles);
        assertEquals(2, act.size());
        for (List<String> group : act)
            assertEquals(1, group.size());
    }

    @Test
    public void when_duplicate_matcher_is_called_for_two_duplicate_articles_then_one_group_with_both_articles_is_returned() {
        String articleNumber1 = fixtureString();
        String articleNumber2 = fixtureString();
        Materials materials = fixtureMaterials();
        ImageGroupCharacteristics imageGroupCharacteristics = fixtureImageGroupCharacteristics();
        articles.add(new Article(articleNumber1, materials, null, false, "", imageGroupCharacteristics));
        articles.add(new Article(articleNumber2, materials, null, false, "", imageGroupCharacteristics));

        List<List<String>> act = cut.matchDuplicatesGroups(articles);
        assertEquals(1, act.size());
        assertTrue(act.get(0).contains(articleNumber1));
        assertTrue(act.get(0).contains(articleNumber2));
    }

    @Test
    public void when_duplicate_matcher_is_called_for_two_duplicate_articles_then_materials_is_used_for_matching() {
        String articleNumber1 = fixtureString();
        String articleNumber2 = fixtureString();
        Materials materials = fixtureMaterials();
        articles.add(new Article(articleNumber1, materials, null, false, "", fixtureImageGroupCharacteristics()));
        articles.add(new Article(articleNumber2, fixtureMaterials(), null, false, "", fixtureImageGroupCharacteristics()));

        List<List<String>> act = cut.matchDuplicatesGroups(articles);
        assertEquals(2, act.size());
        for (List<String> group : act)
            assertEquals(1, group.size());
    }

    @Test
    public void when_duplicate_matcher_is_called_for_duplicate_articles_then_articles_with_different_functions_do_not_matche_as_duplicates() {
        String articleNumber1 = fixtureString();
        String articleNumber2 = fixtureString();
        Materials materials = fixtureMaterials();
        String articleNumber3 = fixtureString();
        HashSet<String> functionsA = new HashSet<>();
        HashSet<String> functionsB = new HashSet<>();
        functionsA.add(fixtureString());
        functionsB.add(fixtureString());
        ImageGroupCharacteristics imageGroupCharacteristics = fixtureImageGroupCharacteristics();
        articles.add(new Article(articleNumber1, materials, null, false, "", imageGroupCharacteristics));
        articles.add(new Article(articleNumber2, materials, functionsA, false, "", imageGroupCharacteristics));
        articles.add(new Article(articleNumber3, materials, functionsB, false, "", imageGroupCharacteristics));

        List<List<String>> act = cut.matchDuplicatesGroups(articles);
        assertEquals(3, act.size());
        assertTrue(act.get(0).contains(articleNumber1));
        assertTrue(act.get(1).contains(articleNumber2));
        assertTrue(act.get(2).contains(articleNumber3));
    }

    @Test
    public void when_duplicate_matcher_is_called_for_two_mirror_articles_then_mirrors_are_matched() {
        String articleNumber1 = fixtureString();
        String articleNumber2 = fixtureString();
        Materials materials = fixtureMaterials();
        ImageGroupCharacteristics imageGroupCharacteristics = fixtureImageGroupCharacteristics();
        articles.add(new Article(articleNumber1, materials, null, false, "", imageGroupCharacteristics));
        articles.add(new Article(articleNumber2, materials, null, true, "", imageGroupCharacteristics));

        List<List<String>> act = cut.matchDuplicatesGroups(articles);
        assertEquals(1, act.size());
        assertTrue(act.get(0).contains(articleNumber1));
        assertTrue(act.get(0).contains(articleNumber2));
    }

    @Test
    public void when_duplicate_matcher_is_called_for_two_furnitures_with_same_image_group_characteristics_then_they_are_matched() {
        String articleNumber1 = fixtureString();
        String articleNumber2 = fixtureString();
        String styleId = fixtureString();
        Materials materials = fixtureMaterials();
        ArrayList<String> height1 = new ArrayList<>();
        ArrayList<String> height2 = new ArrayList<>();
        height1.add("1");
        height2.add("1");
        ImageGroupCharacteristics imageGroupCharacteristics1 = new ImageGroupCharacteristics(styleId, height1, new ArrayList<>(), new ArrayList<>());
        ImageGroupCharacteristics imageGroupCharacteristics2 = new ImageGroupCharacteristics(styleId, height2, new ArrayList<>(), new ArrayList<>());
        articles.add(new Article(articleNumber1, materials, null, false, "", imageGroupCharacteristics1));
        articles.add(new Article(articleNumber2, materials, null, false, "", imageGroupCharacteristics2));

        List<List<String>> act = cut.matchDuplicatesGroups(articles);
        assertEquals(1, act.size());
    }

    @Test
    public void when_duplicate_matcher_is_called_for_two_furnitures_with_different_image_group_characteristics_then_they_are_not_matched() {
        String articleNumber1 = fixtureString();
        String articleNumber2 = fixtureString();
        String styleId = fixtureString();
        Materials materials = fixtureMaterials();
        ArrayList<String> height1 = new ArrayList<>();
        ArrayList<String> height2 = new ArrayList<>();
        height1.add("1");
        height2.add("2");
        ImageGroupCharacteristics imageGroupCharacteristics1 = new ImageGroupCharacteristics(styleId, height1, new ArrayList<>(), new ArrayList<>());
        ImageGroupCharacteristics imageGroupCharacteristics2 = new ImageGroupCharacteristics(styleId, height2, new ArrayList<>(), new ArrayList<>());
        articles.add(new Article(articleNumber1, materials, null, false, "", imageGroupCharacteristics1));
        articles.add(new Article(articleNumber2, materials, null, false, "", imageGroupCharacteristics2));

        List<List<String>> act = cut.matchDuplicatesGroups(articles);
        assertEquals(2, act.size());
    }
}
