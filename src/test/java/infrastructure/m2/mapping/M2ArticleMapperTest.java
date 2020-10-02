package infrastructure.m2.mapping;

import core.article.Article;
import core.article.exceptions.InvalidArticleData;
import utils.ResourceFileReader;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class M2ArticleMapperTest {
    private M2ArticleMapper cut;

    @Before
    public void setup() {
        cut = new M2ArticleMapper();
    }

    private String getJsonFromFile(String filename) {
        try {
            return ResourceFileReader.readResource("article/" + filename + ".json");
        } catch (
                IOException e) {
            throw new RuntimeException();
        }
    }

    @Test
    public void when_article_mapper_is_called_for_article_with_stellvariante_null_then_article_gets_not_a_mirror_flag() throws InvalidArticleData {
        Article act = cut.mapFromJson(getJsonFromFile("mirrors/stellvariante_null"));
        assertEquals(false, act.isMirror());
    }

    @Test
    public void when_article_mapper_is_called_for_article_with_stellvariante_containing_right_then_article_gets_a_mirror_flag() throws InvalidArticleData {
        Article act = cut.mapFromJson(getJsonFromFile("mirrors/stellvariante_rechts"));
        assertEquals(true, act.isMirror());
    }

    @Test
    public void when_article_mapper_is_called_for_article_with_stellvariante_containing_beidseitig_then_article_gets_not_a_mirror_flag() throws InvalidArticleData {
        Article act = cut.mapFromJson(getJsonFromFile("mirrors/stellvariante_beidseitig"));
        assertEquals(false, act.isMirror());
    }

    @Test
    public void when_article_mapper_is_called_for_article_without_stellvariante_then_dimension3_rechts_sets_mirror_flag() throws InvalidArticleData {
        Article act = cut.mapFromJson(getJsonFromFile("mirrors/stellvariante_null_dimension3_rechts"));
        assertEquals(true, act.isMirror());
    }

    @Test
    public void when_article_mapper_is_called_for_article_without_stellvariante_then_dimension3_links_sets_not_mirror_flag() throws InvalidArticleData {
        Article act = cut.mapFromJson(getJsonFromFile("mirrors/stellvariante_null_dimension3_links"));
        assertEquals(false, act.isMirror());
    }

    @Test
    public void when_article_mapper_is_called_for_article_without_stellvariante_and_without_dimension3_then_sets_not_mirror_flag() throws InvalidArticleData {
        Article act = cut.mapFromJson(getJsonFromFile("mirrors/stellvariante_null_dimension3_null"));
        assertEquals(false, act.isMirror());
    }

    @Test
    public void when_article_mapper_is_called_for_article_with_one_value_for_colorreference_then_materialcode_is_set() throws InvalidArticleData {
        Article act = cut.mapFromJson(getJsonFromFile("colorreference/colorreference_one_value"));
        assertEquals("steinpol_lars_braun_99", act.getMaterials().getMaterialCode());
    }

    @Test
    public void when_article_mapper_is_called_for_article_with_multiple_value_for_colorreference_then_materialcode_is_set() throws InvalidArticleData {
        Article act = cut.mapFromJson(getJsonFromFile("colorreference/colorreference_multiple_value"));
        assertEquals("Korp: bleul_microvelour_hellgrau_001/0056 / Sitz+Rücken+Kissen: bleul__anthrazit_004/157", act.getMaterials().getMaterialCode());
    }


    @Test
    public void when_article_mapper_is_called_for_article_with_ausfuehrung_and_ausstattung_then_both_attributes_are_used_as_function() throws InvalidArticleData {
        Article act = cut.mapFromJson(getJsonFromFile("functions/ausfuehrung_and_ausstattung"));
        assertEquals(2, act.getArticleFunctions().size());
    }
    @Test
    public void when_article_mapper_is_called_for_article_with_styleid_legnth_9_then_stylieid_is_correct() throws InvalidArticleData {
        Article act = cut.mapFromJson(getJsonFromFile("style/styleid_lenght_9"));
        assertEquals("123456789", act.getStyleId());
    }

    @Test
    public void when_article_mapper_is_called_for_article_with_styleid_legnth_11_then_stylieid_is_correct() throws InvalidArticleData {
        Article act = cut.mapFromJson(getJsonFromFile("style/styleid_lenght_11"));
        assertEquals("12345678901", act.getStyleId());
    }
}
