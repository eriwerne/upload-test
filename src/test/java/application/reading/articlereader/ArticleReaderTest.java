package application.reading.articlereader;

import application.reading.exception.ArticleNotFound;
import application.reading.exception.ResourceFailure;
import core.article.Article;
import core.article.exceptions.InvalidArticleData;
import injection.ArticleReaderSourceMock;
import org.junit.Before;
import org.junit.Test;
import utils.UnitTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ArticleReaderTest extends UnitTest {
    private ArticleReader cut;
    private ArticleReaderSource mockArticleReaderSource;
    private List<Article> responseArticles;

    private void mockArticleSourceForArticleNumber(List<String> requestArticleNumberList) throws ResourceFailure, ArticleNotFound, InvalidArticleData {
        responseArticles.clear();
        for (String articleNumber : requestArticleNumberList)
            responseArticles.add(createArticleForArticleNumber(articleNumber));
        when(mockArticleReaderSource.readArticles(requestArticleNumberList)).thenReturn(responseArticles);
    }

    @Before
    public void setup() {
        mockArticleReaderSource = new ArticleReaderSourceMock().getArticleReaderSourceMock();
        responseArticles = new ArrayList<>();
        cut = new ArticleReader();
    }

    @Test
    public void when_article_reader_is_called_with_empty_collection_then_no_exception_raises() throws ResourceFailure, InvalidArticleData, ArticleNotFound {
        HashMap<String, Article> act = cut.readArticles(new ArrayList<>());
        assertTrue(act.isEmpty());
    }

    @Test
    public void when_article_reader_is_called_for_one_article_then_it_returns_one_article() throws ResourceFailure, ArticleNotFound, InvalidArticleData {
        String requestArticleNumber = fixtureString();
        List<String> requestArticleList = new ArrayList<>();
        requestArticleList.add(requestArticleNumber);
        mockArticleSourceForArticleNumber(requestArticleList);

        HashMap<String, Article> act = cut.readArticles(requestArticleList);
        assertEquals(1, act.size());
    }

    @Test
    public void when_article_reader_is_called_and_persisted_result_not_exists_then_m2_is_requested() throws ResourceFailure, InvalidArticleData, ArticleNotFound {
        String requestArticleNumber = fixtureString();
        List<String> requestArticleList = new ArrayList<>();
        requestArticleList.add(requestArticleNumber);
        mockArticleSourceForArticleNumber(requestArticleList);
        cut.readArticles(requestArticleList);
        verify(mockArticleReaderSource, times(1)).readArticles(any());
    }

    @Test
    public void when_article_reader_is_called_twice_with_different_articles_then_result_not_contains_first_article_due_to_caching() throws ResourceFailure, InvalidArticleData, ArticleNotFound {
        String articleNumber1 = fixtureString();
        List<String> request1ArticleList = new ArrayList<>();
        request1ArticleList.add(articleNumber1);
        mockArticleSourceForArticleNumber(request1ArticleList);
        cut.readArticles(request1ArticleList);

        List<String> request2ArticleList = new ArrayList<>();
        String articleNumber2 = fixtureString();
        request2ArticleList.add(articleNumber2);
        mockArticleSourceForArticleNumber(request2ArticleList);
        HashMap<String, Article> act2 = cut.readArticles(request2ArticleList);
        assertFalse(act2.containsKey(articleNumber1));
        assertTrue(act2.containsKey(articleNumber2));
    }
}
