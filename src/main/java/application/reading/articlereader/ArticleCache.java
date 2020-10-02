package application.reading.articlereader;

import application.output.Persister;
import application.output.PersisterFailure;
import core.article.Article;
import injection.DaggerArticleCacheInjection;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArticleCache {
    @Inject
    Persister persister;
    private final String cacheFolder = "cache/articles/";
    private final HashMap<String, Article> inMemoryCache;

    public ArticleCache() {
        DaggerArticleCacheInjection.builder().build().inject(this);
        inMemoryCache = new HashMap<>();
    }

    public void initArticles(List<String> articleNumbers) throws PersisterFailure {
        putPersistedArticlesToInMemoryCache(articleNumbers);
    }

    private void putPersistedArticlesToInMemoryCache(List<String> articleNumbers) throws PersisterFailure {
        for (String articleNumber : articleNumbers)
            if (!inMemoryCache.containsKey(articleNumber))
                if (persister.pathExists(cacheFolder, articleNumber)) {
                    Article article = (Article) persister.getObject(cacheFolder, articleNumber);
                    inMemoryCache.put(articleNumber, article);
                }
    }

    public HashMap<String, Article> getCachedArticlesFromList(List<String> articleNumbers) {
        HashMap<String, Article> cachedArticles = new HashMap<>();
        for (String articleNumber : articleNumbers)
            cachedArticles.put(articleNumber, inMemoryCache.get(articleNumber));
        return cachedArticles;
    }

    public ArrayList<String> getNotCachedArticlesFromList(List<String> articleNumbers) {
        ArrayList<String> articlesNotInCache = new ArrayList<>();
        for (String articleNumber : articleNumbers)
            if (!inMemoryCache.containsKey(articleNumber))
                articlesNotInCache.add(articleNumber);
        return articlesNotInCache;
    }

    public void putArticles(List<Article> requestedArticles) throws PersisterFailure {
        for (Article article : requestedArticles) {
            persister.persistObject(cacheFolder, article.getArticleNumber(), article);
            inMemoryCache.put(article.getArticleNumber(), article);
        }
    }
}
