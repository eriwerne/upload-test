package devtools.apiqualityinspection;

import application.output.PersisterFailure;
import application.reading.exception.ArticleNotFound;
import application.reading.exception.ResourceFailure;
import core.article.exceptions.InvalidArticleData;
import org.junit.Before;
import org.junit.Test;
import injection.M2Mock;
import utils.UnitTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArticleMaterialQualityInspectorTest extends UnitTest {
    private ArticleMaterialQualityInspector cut;
    private M2Mock m2Mock;

    @Before
    public void setup() {
        m2Mock = new M2Mock();
        cut = new ArticleMaterialQualityInspector();
    }

    @Test
    public void when_all_articles_in_order_have_material_information_then_it_returns_an_empty_list() throws ResourceFailure, InvalidArticleData, ArticleNotFound, PersisterFailure {
        String articleNumberValid = "123456";

        m2Mock.mockM2ApiResponse("material/article/valid_material/123456.json", articleNumberValid);

        List<String> act = cut.getArticleNumbersWithInvalidMaterial(Arrays.asList(articleNumberValid));

        assertEquals(0, act.size());
    }

    @Test
    public void when_some_articles_in_order_dont_have_material_information_then_it_returns_these_article_numbers() throws ResourceFailure, InvalidArticleData, ArticleNotFound, PersisterFailure {
        String articleNumberValid = "123456";
        String articleNumberInvalid1 = "234567";
        String articleNumberInvalid2 = "345678";

        m2Mock.mockM2ApiResponse("material/article/valid_material/123456.json", articleNumberValid);
        m2Mock.mockM2ApiResponse("material/article/invalid_material/234567.json", articleNumberInvalid1);
        m2Mock.mockM2ApiResponse("material/article/invalid_material/345678.json", articleNumberInvalid2);

        List<String> act = cut.getArticleNumbersWithInvalidMaterial(Arrays.asList(articleNumberInvalid1, articleNumberInvalid2, articleNumberValid));

        assertTrue(act.contains(articleNumberInvalid1));
        assertTrue(act.contains(articleNumberInvalid2));
    }
}
