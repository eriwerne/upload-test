package application.dataprocessing.merging;

import application.dataprocessing.matching.MirrorRelations;
import core.ArticleImageOrderCollection;
import core.article.Article;
import core.image.DetailFotografie;
import core.image.ImageOrder;
import org.junit.Before;
import org.junit.Test;
import utils.UnitTest;

import static org.junit.Assert.*;

public class ImageOrderMirrorMergerTest extends UnitTest {
    private ImageOrderMirrorMerger cut;
    private String articleNumberOrigin;
    private String articleNumberMirror;
    private ArticleImageOrderCollection articleImageOrders;

    @Before
    public void setup() {
        cut = new ImageOrderMirrorMerger();
        articleNumberOrigin = fixtureString();
        articleNumberMirror = fixtureString();

        articleImageOrders = new ArticleImageOrderCollection();
        articleImageOrders.addArticle(new Article(articleNumberOrigin, fixtureMaterials(), null, false, "", fixtureImageGroupCharacteristics()));
        articleImageOrders.addArticle(new Article(articleNumberMirror, fixtureMaterials(), null, true, "", fixtureImageGroupCharacteristics()));
    }

    @Test
    public void when_merger_is_called_for_article_without_imageorder_then_article_remains_in_collection() {
        ArticleImageOrderCollection articleImageOrders = new ArticleImageOrderCollection();
        String articleNumber = fixtureString();
        articleImageOrders.addArticle(createArticleForArticleNumber(articleNumber));
        cut.mergeFilenamesToMirrorImageOrders(new MirrorRelations(), articleImageOrders);
        assertTrue(articleImageOrders.getArticleNumbers().contains(articleNumber));
    }

    @Test
    public void when_merger_is_called_for_one_article_then_image_orders_are_returned() {
        String perspective = fixtureString();
        String filenameOrigin = fixtureString();
        String inhaltsklassifizierung = fixtureString();
        String aufnahmeart = fixtureString();
        ImageOrder imageOrder = new ImageOrder(perspective, listOfFilename(filenameOrigin), inhaltsklassifizierung, aufnahmeart, null);

        articleImageOrders.addImageOrderToArticle(imageOrder, articleNumberOrigin);
        cut.mergeFilenamesToMirrorImageOrders(new MirrorRelations(), articleImageOrders);

        assertEquals(1, articleImageOrders.getImageOrdersForArticle(articleNumberOrigin).size());
        assertTrue(articleImageOrders.getImageOrdersForArticle(articleNumberOrigin).get(0).getFilenames().contains(filenameOrigin));
    }

    @Test
    public void when_merger_is_called_for_two_mirrored_articles_then_matched_image_orders_of_mirror_article_are_not_returned() {
        String filenameMirror = fixtureString();
        String perspective = fixtureString();
        String filenameOrigin = fixtureString();
        String inhaltsklassifizierung = fixtureString();
        String aufnahmeart = fixtureString();
        articleImageOrders.addImageOrderToArticle(new ImageOrder(perspective, listOfFilename(filenameOrigin), inhaltsklassifizierung, aufnahmeart, null), articleNumberOrigin);
        articleImageOrders.addImageOrderToArticle(new ImageOrder(perspective, listOfFilename(filenameMirror), inhaltsklassifizierung, aufnahmeart, null), articleNumberMirror);
        articleImageOrders.addImageOrderToArticle(new ImageOrder(fixtureString(), listOfFilename(filenameMirror), inhaltsklassifizierung, aufnahmeart, null), articleNumberMirror);

        MirrorRelations mirrorOriginMapping = new MirrorRelations();
        mirrorOriginMapping.setOriginArticleForMirrorArticle(articleNumberMirror, articleNumberOrigin);
        cut.mergeFilenamesToMirrorImageOrders(mirrorOriginMapping, articleImageOrders);

        assertEquals(1, articleImageOrders.getImageOrdersForArticle(articleNumberMirror).size());
    }

    @Test
    public void when_merger_is_called_for_two_mirrored_articles_then_filenames_of_mirror_article_appear_in_mirror_filenames_of_origin_article() {
        String filenameMirror = fixtureString();
        String perspective = fixtureString();
        String filenameOrigin = fixtureString();
        String inhaltsklassifizierung = fixtureString();
        String aufnahmeart = fixtureString();
        articleImageOrders.addImageOrderToArticle(new ImageOrder(perspective, listOfFilename(filenameOrigin), inhaltsklassifizierung, aufnahmeart, null), articleNumberOrigin);
        articleImageOrders.addImageOrderToArticle(new ImageOrder(perspective, listOfFilename(filenameMirror), inhaltsklassifizierung, aufnahmeart, null), articleNumberMirror);

        MirrorRelations mirrorOriginMapping = new MirrorRelations();
        mirrorOriginMapping.setOriginArticleForMirrorArticle(articleNumberMirror, articleNumberOrigin);
        cut.moveFilenamesOfMirrorArticles(articleImageOrders);
        cut.mergeFilenamesToMirrorImageOrders(mirrorOriginMapping, articleImageOrders);

        assertTrue(articleImageOrders.getImageOrdersForArticle(articleNumberOrigin).get(0).getFilenamesMirror().contains(filenameMirror));
    }

    @Test
    public void when_merger_is_called_for_two_mirrored_articles_then_mirrors_image_orders_matched_correctly() {
        String inhaltsklassifizierung = fixtureString();
        String aufnahmeart = fixtureString();
        String perspectiveA = fixtureString();
        String perspectiveB = fixtureString();
        String filenameOriginA = fixtureString();
        String filenameMirrorA = fixtureString();
        String filenameOriginB = fixtureString();
        String filenameMirrorB = fixtureString();
        String filenameOriginF = fixtureString();
        String filenameMirrorF = fixtureString();
        articleImageOrders.addImageOrderToArticle(new ImageOrder(perspectiveA, listOfFilename(filenameOriginA), inhaltsklassifizierung, aufnahmeart, null), articleNumberOrigin);
        articleImageOrders.addImageOrderToArticle(new ImageOrder(perspectiveB, listOfFilename(filenameOriginB), inhaltsklassifizierung, aufnahmeart, null), articleNumberOrigin);
        articleImageOrders.addImageOrderToArticle(new ImageOrder(perspectiveA, listOfFilename(filenameOriginF), inhaltsklassifizierung, aufnahmeart, DetailFotografie.FUNKTION), articleNumberOrigin);
        articleImageOrders.addImageOrderToArticle(new ImageOrder(perspectiveA, listOfFilename(filenameMirrorA), inhaltsklassifizierung, aufnahmeart, null), articleNumberMirror);
        articleImageOrders.addImageOrderToArticle(new ImageOrder(perspectiveB, listOfFilename(filenameMirrorB), inhaltsklassifizierung, aufnahmeart, null), articleNumberMirror);
        articleImageOrders.addImageOrderToArticle(new ImageOrder(perspectiveA, listOfFilename(filenameMirrorF), inhaltsklassifizierung, aufnahmeart, DetailFotografie.FUNKTION), articleNumberMirror);

        MirrorRelations mirrorOriginMapping = new MirrorRelations();
        mirrorOriginMapping.setOriginArticleForMirrorArticle(articleNumberMirror, articleNumberOrigin);
        cut.moveFilenamesOfMirrorArticles(articleImageOrders);
        cut.mergeFilenamesToMirrorImageOrders(mirrorOriginMapping, articleImageOrders);

        for (ImageOrder imageOrder : articleImageOrders.getImageOrdersForArticle(articleNumberOrigin)) {
            if (imageOrder.getFilenames().contains(filenameOriginA)) {
                assertTrue(imageOrder.getFilenamesMirror().contains(filenameMirrorA));
                assertFalse(imageOrder.getFilenames().contains(filenameOriginB));
                assertFalse(imageOrder.getFilenamesMirror().contains(filenameMirrorB));
                assertFalse(imageOrder.getFilenames().contains(filenameOriginF));
                assertFalse(imageOrder.getFilenamesMirror().contains(filenameMirrorF));
            }
            if (imageOrder.getFilenames().contains(filenameOriginB)) {
                assertTrue(imageOrder.getFilenamesMirror().contains(filenameMirrorB));
                assertFalse(imageOrder.getFilenames().contains(filenameOriginA));
                assertFalse(imageOrder.getFilenamesMirror().contains(filenameMirrorA));
                assertFalse(imageOrder.getFilenames().contains(filenameOriginF));
                assertFalse(imageOrder.getFilenamesMirror().contains(filenameMirrorF));
            }
            if (imageOrder.getFilenames().contains(filenameOriginF)) {
                assertTrue(imageOrder.getFilenamesMirror().contains(filenameMirrorF));
                assertFalse(imageOrder.getFilenames().contains(filenameOriginA));
                assertFalse(imageOrder.getFilenamesMirror().contains(filenameMirrorA));
                assertFalse(imageOrder.getFilenames().contains(filenameOriginB));
                assertFalse(imageOrder.getFilenamesMirror().contains(filenameMirrorB));
            }
        }
    }

    @Test
    public void when_merger_merges_mirrors_then_not_mirrorable_image_orders_are_merged_to_filenames_not_mirror_filenames() {
        String filenameMirror = fixtureString();
        String perspective = fixtureString();
        String filenameOrigin = fixtureString();
        String inhaltsklassifizierung = fixtureString();
        String aufnahmeart = fixtureString();
        articleImageOrders.addImageOrderToArticle(new ImageOrder(perspective, listOfFilename(filenameOrigin), inhaltsklassifizierung, aufnahmeart, DetailFotografie.MARKENDETAIL), articleNumberOrigin);
        articleImageOrders.addImageOrderToArticle(new ImageOrder(perspective, listOfFilename(filenameMirror), inhaltsklassifizierung, aufnahmeart, DetailFotografie.MARKENDETAIL), articleNumberMirror);

        MirrorRelations mirrorOriginMapping = new MirrorRelations();
        mirrorOriginMapping.setOriginArticleForMirrorArticle(articleNumberMirror, articleNumberOrigin);
        cut.mergeFilenamesToMirrorImageOrders(mirrorOriginMapping, articleImageOrders);

        assertFalse(articleImageOrders.getImageOrdersForArticle(articleNumberOrigin).get(0).getFilenamesMirror().contains(filenameMirror));
        assertTrue(articleImageOrders.getImageOrdersForArticle(articleNumberOrigin).get(0).getFilenames().contains(filenameMirror));
    }

    @Test
    public void when_merger_is_called_then_filenames_of_mirrors_with_no_origin_article_should_be_moved_to_filename_mirrors_in_the_same_object() {
        String filenameMirror = fixtureString();
        String perspective = fixtureString();
        String inhaltsklassifizierung = fixtureString();
        String aufnahmeart = fixtureString();
        articleImageOrders.addImageOrderToArticle(new ImageOrder(perspective, listOfFilename(filenameMirror), inhaltsklassifizierung, aufnahmeart, DetailFotografie.NULL), articleNumberMirror);

        MirrorRelations mirrorOriginMapping = new MirrorRelations();
        mirrorOriginMapping.setOriginArticleForMirrorArticle(articleNumberMirror, null);
        cut.moveFilenamesOfMirrorArticles(articleImageOrders);
        cut.mergeFilenamesToMirrorImageOrders(mirrorOriginMapping, articleImageOrders);

        assertFalse(articleImageOrders.getImageOrdersForArticle(articleNumberMirror).get(0).getFilenames().contains(filenameMirror));
        assertTrue(articleImageOrders.getImageOrdersForArticle(articleNumberMirror).get(0).getFilenamesMirror().contains(filenameMirror));
    }

    @Test
    public void when_merger_is_called_then_filenames_of_mirrors_with_no_origin_image_order_should_be_moved_to_filename_mirrors_in_the_same_object() {
        String filenameMirror = fixtureString();
        String perspective = fixtureString();
        String filenameOrigin = fixtureString();
        String inhaltsklassifizierung = fixtureString();
        String aufnahmeart = fixtureString();
        articleImageOrders.addImageOrderToArticle(new ImageOrder(perspective, listOfFilename(filenameOrigin), inhaltsklassifizierung, aufnahmeart, DetailFotografie.MARKENDETAIL), articleNumberOrigin);
        articleImageOrders.addImageOrderToArticle(new ImageOrder(perspective, listOfFilename(filenameMirror), inhaltsklassifizierung, aufnahmeart, DetailFotografie.NULL), articleNumberMirror);

        MirrorRelations mirrorOriginMapping = new MirrorRelations();
        mirrorOriginMapping.setOriginArticleForMirrorArticle(articleNumberMirror, articleNumberOrigin);
        cut.moveFilenamesOfMirrorArticles(articleImageOrders);
        cut.mergeFilenamesToMirrorImageOrders(mirrorOriginMapping, articleImageOrders);

        assertFalse(articleImageOrders.getImageOrdersForArticle(articleNumberMirror).get(0).getFilenames().contains(filenameMirror));
        assertTrue(articleImageOrders.getImageOrdersForArticle(articleNumberMirror).get(0).getFilenamesMirror().contains(filenameMirror));
    }

    @Test
    public void when_merger_is_called_then_filenames_of_not_mirrorable_image_orders_with_no_origin_image_order_should_not_be_moved_to_filename_mirrors_in_the_same_object() {
        String filenameMirror = fixtureString();
        String perspective = fixtureString();
        String filenameOrigin = fixtureString();
        String inhaltsklassifizierung = fixtureString();
        String aufnahmeart = fixtureString();
        articleImageOrders.addImageOrderToArticle(new ImageOrder(perspective, listOfFilename(filenameOrigin), inhaltsklassifizierung, aufnahmeart, DetailFotografie.MARKENDETAIL), articleNumberOrigin);
        articleImageOrders.addImageOrderToArticle(new ImageOrder(perspective, listOfFilename(filenameMirror), inhaltsklassifizierung, aufnahmeart, DetailFotografie.DIGITALESSTOFFMUSTER), articleNumberMirror);

        MirrorRelations mirrorOriginMapping = new MirrorRelations();
        mirrorOriginMapping.setOriginArticleForMirrorArticle(articleNumberMirror, articleNumberOrigin);
        cut.mergeFilenamesToMirrorImageOrders(mirrorOriginMapping, articleImageOrders);

        assertTrue(articleImageOrders.getImageOrdersForArticle(articleNumberMirror).get(0).getFilenames().contains(filenameMirror));
        assertFalse(articleImageOrders.getImageOrdersForArticle(articleNumberMirror).get(0).getFilenamesMirror().contains(filenameMirror));
    }

    @Test
    public void when_merger_is_called_for_articles_were_one_mirror_has_no_origin_then_not_mirrorable_image_filenames_of_mirrors_with_no_origin_should_not_be_moved_to_filename_mirrors_in_the_same_object() {
        String perspective = fixtureString();
        String inhaltsklassifizierung = fixtureString();
        String aufnahmeart = fixtureString();
        String filenameMirrorWithNoOrigin = fixtureString();
        articleImageOrders.addImageOrderToArticle(new ImageOrder(perspective, listOfFilename(filenameMirrorWithNoOrigin), inhaltsklassifizierung, aufnahmeart, DetailFotografie.MARKENDETAIL), articleNumberMirror);

        MirrorRelations mirrorOriginMapping = new MirrorRelations();
        mirrorOriginMapping.setOriginArticleForMirrorArticle(articleNumberMirror, null);
        cut.mergeFilenamesToMirrorImageOrders(mirrorOriginMapping, articleImageOrders);

        assertTrue(articleImageOrders.getImageOrdersForArticle(articleNumberMirror).get(0).getFilenames().contains(filenameMirrorWithNoOrigin));
        assertFalse(articleImageOrders.getImageOrdersForArticle(articleNumberMirror).get(0).getFilenamesMirror().contains(filenameMirrorWithNoOrigin));
    }
}
