package devtools.apiqualityinspection;

import application.output.PersisterFailure;
import application.reading.exception.ArticleNotFound;
import application.reading.exception.ResourceFailure;
import core.article.exceptions.InvalidArticleData;
import org.junit.Before;
import org.junit.Test;
import injection.M2Mock;
import utils.UnitTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArticleFunctionQualityInspectorTest extends UnitTest {
    private ArticleFunctionQualityInspector cut;
    private M2Mock m2Mock;

    @Before
    public void setup() {
        m2Mock = new M2Mock();
        cut = new ArticleFunctionQualityInspector();
    }

    @Test
    public void when_articles_with_functions_are_checked_then_it_returns_a_set_with_all_functions_their_styles_and_their_articlenumbers() throws ResourceFailure, InvalidArticleData, ArticleNotFound, PersisterFailure {
        String article1FunctionA = "10297421";
        String article2FunctionA = "10297422";
        String article1FunctionB = "10297423";
        m2Mock.mockM2ApiResponse("article/functions/10297421_function_a_ausfuehrung.json", article1FunctionA);
        m2Mock.mockM2ApiResponse("article/functions/10297422_function_a_ausstattung.json", article2FunctionA);
        m2Mock.mockM2ApiResponse("article/functions/10297423_function_b_ausstattung.json", article1FunctionB);

        HashMap<String, HashMap<String, HashMap<String, String>>> act = cut.getArticleFunctions(Arrays.asList(article1FunctionA, article2FunctionA, article1FunctionB));

        assertEquals(2, act.size());
        assertTrue(act.containsKey("Function A"));
        assertTrue(act.containsKey("Function B"));
        assertEquals(2, getAllArticleNumbersForFunction(act, "Function A").size());
        assertEquals(1, getAllArticleNumbersForFunction(act, "Function B").size());
        assertTrue(getAllArticleNumbersForFunction(act, "Function A").contains(article1FunctionA));
        assertTrue(getAllArticleNumbersForFunction(act, "Function A").contains(article2FunctionA));
        assertTrue(getAllArticleNumbersForFunction(act, "Function B").contains(article1FunctionB));
    }

    private List<String> getAllArticleNumbersForFunction(HashMap<String, HashMap<String, HashMap<String, String>>> act, String function) {
        HashMap<String, HashMap<String, String>> styleArticles = act.get(function);
        ArrayList<String> articleNumbers = new ArrayList<>();
        for (String style : new ArrayList<>(styleArticles.keySet()))
            articleNumbers.addAll(styleArticles.get(style).keySet());
        return articleNumbers;
    }
}
