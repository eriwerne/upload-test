package application.reading.articlereader;

import application.output.Persister;
import application.output.PersisterFailure;
import application.reading.exception.ArticleNotFound;
import application.reading.exception.ResourceFailure;
import core.article.Article;
import core.article.exceptions.InvalidArticleData;
import injection.ArticleReaderSourceMock;
import injection.PersisterMock;
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
    private Persister mockPersister;
    private List<Article> responseArticles;
    private final String downloadFolder = "cache/articles/";

    private void mockArticleSourceForArticleNumber(List<String> requestArticleNumberList) throws ResourceFailure, ArticleNotFound, InvalidArticleData {
        responseArticles.clear();
        for (String articleNumber : requestArticleNumberList)
            responseArticles.add(createArticleForArticleNumber(articleNumber));
        when(mockArticleReaderSource.readArticles(requestArticleNumberList)).thenReturn(responseArticles);
    }

    @Before
    public void setup() {
        mockArticleReaderSource = new ArticleReaderSourceMock().getArticleReaderSourceMock();
        mockPersister = new PersisterMock().getPersisterMock();
        responseArticles = new ArrayList<>();
        cut = new ArticleReader();
    }

    @Test
    public void when_article_reader_is_called_with_empty_collection_then_no_exception_raises() throws ResourceFailure, InvalidArticleData, ArticleNotFound, PersisterFailure {
        HashMap<String, Article> act = cut.readArticles(new ArrayList<>());
        assertTrue(act.isEmpty());
    }

    @Test
    public void when_article_reader_is_called_for_one_article_then_it_returns_one_article() throws ResourceFailure, ArticleNotFound, InvalidArticleData, PersisterFailure {
        String requestArticleNumber = fixtureString();
        List<String> requestArticleList = new ArrayList<>();
        requestArticleList.add(requestArticleNumber);
        mockArticleSourceForArticleNumber(requestArticleList);

        HashMap<String, Article> act = cut.readArticles(requestArticleList);
        assertEquals(1, act.size());
    }

    @Test
    public void when_article_reader_is_called_then_persisted_result_is_checked() throws ResourceFailure, InvalidArticleData, ArticleNotFound, PersisterFailure {
        String requestArticleNumber = fixtureString();
        List<String> requestArticleList = new ArrayList<>();
        requestArticleList.add(requestArticleNumber);
        mockArticleSourceForArticleNumber(requestArticleList);
        cut.readArticles(requestArticleList);
        verify(mockPersister, times(1)).pathExists(downloadFolder, requestArticleNumber);
    }

    @Test
    public void when_article_reader_is_called_and_persisted_result_not_exists_then_m2_is_requested() throws ResourceFailure, InvalidArticleData, ArticleNotFound, PersisterFailure {
        String requestArticleNumber = fixtureString();
        List<String> requestArticleList = new ArrayList<>();
        requestArticleList.add(requestArticleNumber);
        mockArticleSourceForArticleNumber(requestArticleList);
        cut.readArticles(requestArticleList);
        verify(mockArticleReaderSource, times(1)).readArticles(any());
    }

    @Test
    public void when_article_reader_is_called_and_persisted_result_not_exists_then_result_is_persisted() throws ResourceFailure, InvalidArticleData, ArticleNotFound, PersisterFailure {
        String requestArticleNumber = fixtureString();
        List<String> requestArticleList = new ArrayList<>();
        requestArticleList.add(requestArticleNumber);
        mockArticleSourceForArticleNumber(requestArticleList);
        cut.readArticles(requestArticleList);
        verify(mockPersister, times(1)).persistObject(eq(downloadFolder), eq(requestArticleNumber), any());
    }

    @Test
    public void when_article_reader_is_called_and_persisted_result_not_exists_then_result_is_not_loaded() throws ResourceFailure, InvalidArticleData, ArticleNotFound, PersisterFailure {
        String requestArticleNumber = fixtureString();
        List<String> requestArticleList = new ArrayList<>();
        requestArticleList.add(requestArticleNumber);
        mockArticleSourceForArticleNumber(requestArticleList);
        cut.readArticles(requestArticleList);
        verify(mockPersister, times(0)).getObject(eq(downloadFolder), eq(requestArticleNumber));
    }

    @Test
    public void when_article_reader_is_called_and_persisted_result_exists_then_m2_is_not_requested() throws ResourceFailure, InvalidArticleData, ArticleNotFound, PersisterFailure {
        String requestArticleNumber = fixtureString();
        List<String> requestArticleList = new ArrayList<>();
        when(mockPersister.pathExists(downloadFolder, requestArticleNumber)).thenReturn(true);
        requestArticleList.add(requestArticleNumber);
        mockArticleSourceForArticleNumber(requestArticleList);
        cut.readArticles(requestArticleList);
        verify(mockArticleReaderSource, times(0)).readArticles(any());
    }

    @Test
    public void when_article_reader_is_called_and_persisted_result_exists_then_result_is_not_persisted() throws ResourceFailure, InvalidArticleData, ArticleNotFound, PersisterFailure {
        String requestArticleNumber = fixtureString();
        List<String> requestArticleList = new ArrayList<>();
        when(mockPersister.pathExists(downloadFolder, requestArticleNumber)).thenReturn(true);
        requestArticleList.add(requestArticleNumber);
        mockArticleSourceForArticleNumber(requestArticleList);
        cut.readArticles(requestArticleList);
        verify(mockPersister, times(0)).persistObject(eq(downloadFolder), eq(requestArticleNumber), any());
    }

    @Test
    public void when_article_reader_is_called_then_persister_is_checked_for_cached_article() throws ResourceFailure, InvalidArticleData, ArticleNotFound, PersisterFailure {
        String requestArticleNumber = fixtureString();
        List<String> requestArticleList = new ArrayList<>();
        when(mockPersister.pathExists(downloadFolder, requestArticleNumber)).thenReturn(false);
        requestArticleList.add(requestArticleNumber);
        mockArticleSourceForArticleNumber(requestArticleList);
        cut.readArticles(requestArticleList);
        verify(mockPersister, times(1)).pathExists(eq(downloadFolder), eq(requestArticleNumber));
    }

    @Test
    public void when_article_reader_is_called_twice_for_same_article_then_persister_is_not_checked_twice_for_cached_article() throws ResourceFailure, InvalidArticleData, ArticleNotFound, PersisterFailure {
        String requestArticleNumber = fixtureString();
        List<String> requestArticleList = new ArrayList<>();
        when(mockPersister.pathExists(downloadFolder, requestArticleNumber)).thenReturn(false);
        requestArticleList.add(requestArticleNumber);
        mockArticleSourceForArticleNumber(requestArticleList);
        cut.readArticles(requestArticleList);
        cut.readArticles(requestArticleList);
        verify(mockPersister, times(1)).pathExists(eq(downloadFolder), eq(requestArticleNumber));
    }

    @Test
    public void when_article_reader_is_called_and_persisted_result_exists_then_result_is_loaded() throws ResourceFailure, InvalidArticleData, ArticleNotFound, PersisterFailure {
        String requestArticleNumber = fixtureString();
        List<String> requestArticleList = new ArrayList<>();
        when(mockPersister.pathExists(downloadFolder, requestArticleNumber)).thenReturn(true);
        requestArticleList.add(requestArticleNumber);
        mockArticleSourceForArticleNumber(requestArticleList);
        cut.readArticles(requestArticleList);
        verify(mockPersister, times(1)).getObject(eq(downloadFolder), eq(requestArticleNumber));
    }

    @Test
    public void when_article_reader_is_called_twice_with_different_articles_then_result_not_contains_first_article_due_to_caching() throws ResourceFailure, InvalidArticleData, ArticleNotFound, PersisterFailure {
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
