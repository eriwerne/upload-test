package application.dataprocessing.filtering;

import core.ArticleImageOrderCollection;
import core.image.DetailFotografie;
import core.image.ImageOrder;
import org.junit.Before;
import org.junit.Test;
import utils.UnitTest;

import static org.junit.Assert.assertEquals;

public class FilterForNonFunctionArticlesTest extends UnitTest {
    private FilterForNonFunctionArticles cut;
    private String articleNumberA;
    private String articleNumberB;

    @Before
    public void setup() {
        articleNumberA = fixtureString();
        articleNumberB = fixtureString();
        cut = new FilterForNonFunctionArticles();
    }

    @Test
    public void when_filter_is_called_with_empty_collection_then_no_exception_raises() {
        cut.filter(new ArticleImageOrderCollection());
    }

    @Test
    public void when_filter_is_called_for_article_without_imageorder_then_no_exception_raises() {
        ArticleImageOrderCollection articleImageOrders = new ArticleImageOrderCollection();
        articleImageOrders.addArticle(createArticleForArticleNumber(""));
        cut.filter(articleImageOrders);
    }

    @Test
    public void when_filter_is_called_for_one_article_with_one_imageorder_then_no_image_is_filtered() {
        ArticleImageOrderCollection articleImageOrders = new ArticleImageOrderCollection();
        articleImageOrders.addArticle(createArticleForArticleNumber(articleNumberA));
        articleImageOrders.addImageOrderToArticle(fixtureImageOrder(), createArticleForArticleNumber(articleNumberA));

        cut.filter(articleImageOrders);
        assertEquals(1, articleImageOrders.getArticlesAsList().size());
        assertEquals(1, articleImageOrders.getImageOrdersForArticle(articleNumberA).size());
    }

    @Test
    public void when_filter_is_called_for_two_articles_with_each_two_imageorders_then_no_image_is_filteredd() {
        ArticleImageOrderCollection articleImageOrders = new ArticleImageOrderCollection();
        articleImageOrders.addArticle(createArticleForArticleNumber(articleNumberA));
        articleImageOrders.addArticle(createArticleForArticleNumber(articleNumberB));
        articleImageOrders.addImageOrderToArticle(fixtureImageOrder(), articleNumberA);
        articleImageOrders.addImageOrderToArticle(fixtureImageOrder(), articleNumberA);
        articleImageOrders.addImageOrderToArticle(fixtureImageOrder(), articleNumberB);
        articleImageOrders.addImageOrderToArticle(fixtureImageOrder(), articleNumberB);

        cut.filter(articleImageOrders);
        assertEquals(2, articleImageOrders.getArticlesAsList().size());
        assertEquals(2, articleImageOrders.getImageOrdersForArticle(articleNumberA).size());
        assertEquals(2, articleImageOrders.getImageOrdersForArticle(articleNumberB).size());
    }

    @Test
    public void when_filter_is_called_for_non_function_article_with_function_imageorder_then_the_function_image_order_is_removed() {
        ArticleImageOrderCollection articleImageOrders = new ArticleImageOrderCollection();
        articleImageOrders.addImageOrderToArticle(new ImageOrder(fixtureString(), fixtureList(), fixtureString(), fixtureString(), DetailFotografie.FUNKTION), createArticleForArticleNumber(articleNumberA));
        articleImageOrders.addImageOrderToArticle(new ImageOrder(fixtureString(), fixtureList(), fixtureString(), fixtureString(), DetailFotografie.MARKENDETAIL), createArticleForArticleNumber(articleNumberA));

        cut.filter(articleImageOrders);
        assertEquals(1, articleImageOrders.getArticlesAsList().size());
        assertEquals(1, articleImageOrders.getImageOrdersForArticle(articleNumberA).size());
    }
}
