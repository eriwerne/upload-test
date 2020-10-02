package application.dataprocessing.matching;

import core.article.Article;
import core.article.ImageGroupCharacteristics;
import core.article.Materials;
import org.junit.Before;
import org.junit.Test;
import utils.UnitTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class ArticleMirrorMatcherTest extends UnitTest {
    private ArticleMirrorMatcher cut;
    private String articleNumberMirror;
    private String articleNumberOrigin;

    @Before
    public void setup() {
        cut = new ArticleMirrorMatcher();
        articleNumberOrigin = fixtureString();
        articleNumberMirror = fixtureString();
    }

    @Test
    public void when_mirror_matching_is_called_with_empty_collection_then_not_null_is_returned() {
        MirrorRelations act = cut.getMirrorRelations(new ArrayList<>());
        assertNotNull(act);
    }

    @Test
    public void when_mirror_matching_is_called_for_article_list_with_no_mirrors_then_it_returns_an_empty_list() {
        List<Article> articles = new ArrayList<>();
        articles.add(createArticleForArticleNumber(fixtureString()));
        articles.add(createArticleForArticleNumber(fixtureString()));
        HashMap<String, String> act = cut.getMirrorRelations(articles).getMirrorOriginRelation();
        assertEquals(0, act.size());
    }

    @Test
    public void when_mirror_matching_is_called_for_mirror_article_list_with_no_matching_origin_then_it_returns_the_mirror_without_origin() {
        List<Article> articles = new ArrayList<>();
        articles.add(createArticleForArticleNumber(fixtureString()));
        Article articleMirror = new Article(fixtureString(), fixtureMaterials(), null, true, "", fixtureImageGroupCharacteristics());
        articles.add(articleMirror);
        HashMap<String, String> act = cut.getMirrorRelations(articles).getMirrorOriginRelation();
        assertEquals(1, act.size());

        assertNull(act.get(articleMirror.getArticleNumber()));
    }

    @Test
    public void when_mirror_matching_is_called_for_two_mirrors_then_it_returns_a_map_of_both_articles() {
        List<Article> articles = new ArrayList<>();
        Materials materials = fixtureMaterials();
        ImageGroupCharacteristics imageGroupCharacteristics = fixtureImageGroupCharacteristics();
        articles.add(new Article(articleNumberOrigin, materials, null, false, "", imageGroupCharacteristics));
        articles.add(new Article(articleNumberMirror, materials, null, true, "", imageGroupCharacteristics));
        HashMap<String, String> act = cut.getMirrorRelations(articles).getMirrorOriginRelation();
        assertEquals(1, act.size());

        assertTrue(act.containsKey(articleNumberMirror));
        assertTrue(act.containsValue(articleNumberOrigin));
    }

    @Test
    public void when_mirror_matching_is_called_for_two_mirrors_then_materials_is_used_for_matching() {
        List<Article> articles = new ArrayList<>();
        ImageGroupCharacteristics imageGroupCharacteristics = fixtureImageGroupCharacteristics();
        articles.add(new Article(articleNumberOrigin, fixtureMaterials(), null, false, "", imageGroupCharacteristics));
        articles.add(new Article(articleNumberMirror, fixtureMaterials(), null, true, "", imageGroupCharacteristics));
        HashMap<String, String> act = cut.getMirrorRelations(articles).getMirrorOriginRelation();

        assertNull(act.get(articleNumberMirror));
    }

    @Test
    public void when_mirror_matching_is_called_for_two_mirrors_then_functions_are_used_for_matching() {
        List<Article> articles = new ArrayList<>();
        Materials materials = fixtureMaterials();
        HashSet<String> functionsA = new HashSet<>();
        functionsA.add(fixtureString());
        HashSet<String> functionsB = new HashSet<>();
        functionsB.add(fixtureString());
        ImageGroupCharacteristics imageGroupCharacteristics = fixtureImageGroupCharacteristics();
        articles.add(new Article(articleNumberOrigin, materials, functionsA, false, "", imageGroupCharacteristics));
        articles.add(new Article(articleNumberMirror, materials, functionsB, true, "", imageGroupCharacteristics));
        articles.add(new Article(fixtureString(), materials, null, false, "", imageGroupCharacteristics));
        HashMap<String, String> act = cut.getMirrorRelations(articles).getMirrorOriginRelation();

        assertNull(act.get(articleNumberMirror));
    }
}
