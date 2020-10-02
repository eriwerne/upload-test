package core.article;

import org.junit.Test;
import utils.UnitTest;

import java.util.HashSet;

import static org.junit.Assert.*;

public class ArticleTest extends UnitTest {

    @Test
    public void when_article_is_constructed_with_a_function_called_ohne_funktion_then_this_function_is_dropped() {
        HashSet<String> functions = new HashSet<>();
        functions.add("ohne Funktion");
        Article article = createArticleForFunction(functions);

        assertFalse(article.hasFunction());
    }

    @Test
    public void when_article_is_constructed_with_a_function_containing_ohne_with_capital_o_then_this_function_is_dropped() {
        HashSet<String> functions = new HashSet<>();
        functions.add("Ohne Bettfunktion");
        Article article = createArticleForFunction(functions);

        assertFalse(article.hasFunction());
    }

    @Test
    public void when_article_is_constructed_with_a_function_containing_ohne_and_some_additional_content_then_this_function_is_kept() {
        HashSet<String> functions = new HashSet<>();
        String function1 = "ohne Bettfunktion aber XY-Funktion";
        String function2 = "ohne Zubehör incl. Bettfunktion";
        functions.add(function1);
        functions.add(function2);
        Article article = createArticleForFunction(functions);

        assertTrue(article.getArticleFunctionDescription().contains(function1));
        assertTrue(article.getArticleFunctionDescription().contains(function2));
    }

    @Test
    public void when_article_is_constructed_with_a_function_called_ohne_funktion_and_another_function_then_both_functions_are_kept() {
        HashSet<String> functions = new HashSet<>();
        functions.add("ohne Bettfunktion");
        functions.add("Stauraum");
        Article article = createArticleForFunction(functions);

        assertEquals(2, article.getArticleFunctions().size());
    }

    @Test
    public void when_article_is_constructed_with_a_string_containing_only_function_starting_with_ohne_then_all_functions_are_dropped() {
        HashSet<String> functions = new HashSet<>();
        functions.add("ohne Bettfunktion, ohne Bettkasten, ohne Kopfstütze");
        Article article = createArticleForFunction(functions);
        assertEquals(0, article.getArticleFunctions().size());
    }
}
