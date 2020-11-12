package devtools.apiqualityinspection;

import application.reading.exception.ArticleNotFound;
import application.reading.exception.ResourceFailure;
import core.article.exceptions.InvalidArticleData;
import org.junit.Before;
import org.junit.Test;
import injection.M2Mock;
import utils.UnitTest;

import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.*;

public class ArticleMaterialQualityInspectorTest extends UnitTest {
    private ArticleMaterialQualityInspector cut;
    private M2Mock m2Mock;

    @Before
    public void setup() {
        m2Mock = new M2Mock();
        cut = new ArticleMaterialQualityInspector();
    }

    @Test
    public void when_all_articles_in_order_have_valid_material_information_then_it_returns_an_empty_list() throws ResourceFailure, InvalidArticleData, ArticleNotFound {
        String articleNumberValid = "123456";

        m2Mock.mockM2ApiResponse("material/article/valid_material/123456.json", articleNumberValid);

        Map<String, String> act = cut.getInvalidArticleMaterialId(Arrays.asList(articleNumberValid));

        assertEquals(0, act.size());
    }

    @Test
    public void when_some_articles_in_order_have_material_information_null_then_it_returns_these_article_numbers() throws ResourceFailure, InvalidArticleData, ArticleNotFound {
        String articleNumberValid = "123456";
        String articleNumberInvalid1 = "234567";
        String articleNumberInvalid2 = "345678";

        m2Mock.mockM2ApiResponse("material/article/valid_material/123456.json", articleNumberValid);
        m2Mock.mockM2ApiResponse("material/article/invalid_material/234567.json", articleNumberInvalid1);
        m2Mock.mockM2ApiResponse("material/article/invalid_material/345678.json", articleNumberInvalid2);

        Map<String, String> act = cut.getInvalidArticleMaterialId(Arrays.asList(articleNumberInvalid1, articleNumberInvalid2, articleNumberValid));

        assertTrue(act.containsKey(articleNumberInvalid1));
        assertNull(act.get(articleNumberInvalid1));
        assertTrue(act.containsKey(articleNumberInvalid2));
        assertNull(act.get(articleNumberInvalid2));
    }

    @Test
    public void when_some_articles_in_order_have_material_information_containing_jpg_then_it_returns_these_article_numbers() throws ResourceFailure, InvalidArticleData, ArticleNotFound {
        String articleNumberValid = "123456";
        String articleNumberInvalid1 = "456789";

        m2Mock.mockM2ApiResponse("material/article/valid_material/123456.json", articleNumberValid);
        m2Mock.mockM2ApiResponse("material/article/invalid_material/456789.json", articleNumberInvalid1);

        Map<String, String> act = cut.getInvalidArticleMaterialId(Arrays.asList(articleNumberInvalid1, articleNumberValid));

        assertTrue(act.containsKey(articleNumberInvalid1));
        assertEquals("steinpol_altara_nubuck_blau.jpg", act.get(articleNumberInvalid1));
    }
}
