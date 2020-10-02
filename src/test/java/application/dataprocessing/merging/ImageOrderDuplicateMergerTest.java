package application.dataprocessing.merging;

import core.ArticleImageOrderCollection;
import core.article.Article;
import core.article.Materials;
import core.image.DetailFotografie;
import core.image.ImageOrder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.UnitTest;

import java.util.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class ImageOrderDuplicateMergerTest extends UnitTest {
    private ImageOrderDuplicateMerger cut;

    private Article articleNoFunction;
    private Article articleWithFunction;
    private String filenameDuplicate1;
    private String filenameDuplicate2;
    private String filenameDuplicateMirror1;
    private String filenameDuplicateMirror2;
    private ImageOrder imageOrderDuplicate1;
    private ImageOrder imageOrderDuplicate2;

    private void assertListOfImageOrdersContainsFilename(List<ImageOrder> imageOrders, String filename) {
        for (ImageOrder imageOrder : imageOrders)
            if (imageOrder.getFilenames().contains(filename))
                return;
        fail("filename was not found");
    }

    private void assertListOfImageOrdersNotContainsFilename(List<ImageOrder> imageOrders, String filename) {
        for (ImageOrder imageOrder : imageOrders)
            if (imageOrder.getFilenames().contains(filename))
                fail("filename was found");
    }

    @Before
    public void setup() {
        cut = new ImageOrderDuplicateMerger();
        Materials materials = fixtureMaterials();
        filenameDuplicate1 = fixtureString();
        filenameDuplicate2 = fixtureString();
        filenameDuplicateMirror1 = fixtureString();
        filenameDuplicateMirror2 = fixtureString();
        HashSet<String> functionsArticle2 = new HashSet<>();
        functionsArticle2.add(fixtureString());
        articleNoFunction = new Article(fixtureString(), materials, null, false, "", fixtureImageGroupCharacteristics());
        articleWithFunction = new Article(fixtureString(), materials, functionsArticle2, false, "", fixtureImageGroupCharacteristics());

        String perspective = fixtureString();
        String inhaltsklassifizierung = fixtureString();
        String aufnahmeart = fixtureString();
        imageOrderDuplicate1 = new ImageOrder(perspective, listOfFilename(filenameDuplicate1), inhaltsklassifizierung, aufnahmeart, null);
        imageOrderDuplicate2 = new ImageOrder(perspective, listOfFilename(filenameDuplicate2), inhaltsklassifizierung, aufnahmeart, null);
        imageOrderDuplicate1.getFilenamesMirror().add(filenameDuplicateMirror1);
        imageOrderDuplicate2.getFilenamesMirror().add(filenameDuplicateMirror2);
    }

    @Test
    public void when_imageorderduplicatemerger_is_called_for_article_without_imageorder_then_article_remains_in_collection() {
        ArticleImageOrderCollection articleImageOrders = new ArticleImageOrderCollection();
        String articleNumber = fixtureString();
        articleImageOrders.addArticle(createArticleForArticleNumber(articleNumber));
        cut.mergeFilenamesToDuplicateImageOrders(new ArrayList<>(), articleImageOrders);
        Assert.assertTrue(articleImageOrders.getArticleNumbers().contains(articleNumber));
    }

    @Test
    public void when_imageorderduplicatemerger_is_called_for_different_articles_then_filenames_are_not_merged() {
        ArticleImageOrderCollection articleImageOrders = new ArticleImageOrderCollection();
        Article article1 = createArticleForArticleNumber(fixtureString());
        Article article2 = createArticleForArticleNumber(fixtureString());
        articleImageOrders.addImageOrderToArticle(imageOrderDuplicate1, article1);
        articleImageOrders.addImageOrderToArticle(imageOrderDuplicate2, article2);

        List<List<String>> duplicatesGroups = new ArrayList<>();
        duplicatesGroups.add(Arrays.asList(article1.getArticleNumber()));
        duplicatesGroups.add(Arrays.asList(article2.getArticleNumber()));

        cut.mergeFilenamesToDuplicateImageOrders(duplicatesGroups, articleImageOrders);

        List<ImageOrder> act1 = articleImageOrders.getImageOrdersForArticle(article1.getArticleNumber());
        List<ImageOrder> act2 = articleImageOrders.getImageOrdersForArticle(article2.getArticleNumber());

        assertTrue(act1.get(0).getFilenames().contains(filenameDuplicate1));
        assertFalse(act1.get(0).getFilenames().contains(filenameDuplicate2));
        assertFalse(act2.get(0).getFilenames().contains(filenameDuplicate1));
        assertTrue(act2.get(0).getFilenames().contains(filenameDuplicate2));
    }

    @Test
    public void when_imageorderduplicatemerger_is_called_for_duplicates_then_filenames_of_duplicates_are_merged() {
        ArticleImageOrderCollection articleImageOrder = new ArticleImageOrderCollection();
        articleImageOrder.addImageOrderToArticle(imageOrderDuplicate1, articleNoFunction);
        articleImageOrder.addImageOrderToArticle(imageOrderDuplicate2, articleWithFunction);

        List<List<String>> duplicatesGroups = new ArrayList<>();
        duplicatesGroups.add(Arrays.asList(articleNoFunction.getArticleNumber(), articleWithFunction.getArticleNumber()));
        cut.mergeFilenamesToDuplicateImageOrders(duplicatesGroups, articleImageOrder);

        List<ImageOrder> act1 = articleImageOrder.getImageOrdersForArticle(articleNoFunction.getArticleNumber());
        List<ImageOrder> act2 = articleImageOrder.getImageOrdersForArticle(articleWithFunction.getArticleNumber());

        assertTrue(act1.get(0).getFilenames().contains(filenameDuplicate1));
        assertTrue(act1.get(0).getFilenames().contains(filenameDuplicate2));
        assertTrue(act2.get(0).getFilenames().contains(filenameDuplicate1));
        assertTrue(act2.get(0).getFilenames().contains(filenameDuplicate2));
    }

    @Test
    public void when_imageorderduplicatemerger_is_called_then_mirror_filenames_of_duplicates_are_merged() {
        ArticleImageOrderCollection articleImageOrder = new ArticleImageOrderCollection();
        articleImageOrder.addImageOrderToArticle(imageOrderDuplicate1, articleNoFunction);
        articleImageOrder.addImageOrderToArticle(imageOrderDuplicate2, articleWithFunction);

        List<List<String>> duplicatesGroups = new ArrayList<>();
        duplicatesGroups.add(Arrays.asList(articleNoFunction.getArticleNumber(), articleWithFunction.getArticleNumber()));
        cut.mergeFilenamesToDuplicateImageOrders(duplicatesGroups, articleImageOrder);

        List<ImageOrder> act1 = articleImageOrder.getImageOrdersForArticle(articleNoFunction.getArticleNumber());
        List<ImageOrder> act2 = articleImageOrder.getImageOrdersForArticle(articleWithFunction.getArticleNumber());

        assertTrue(act1.get(0).getFilenamesMirror().contains(filenameDuplicateMirror1));
        assertTrue(act1.get(0).getFilenamesMirror().contains(filenameDuplicateMirror2));
        assertTrue(act2.get(0).getFilenamesMirror().contains(filenameDuplicateMirror1));
        assertTrue(act2.get(0).getFilenamesMirror().contains(filenameDuplicateMirror2));
    }

    @Test
    public void when_imageorderduplicatemerger_is_called_then_unique_images_are_ignored() {
        ArticleImageOrderCollection articleImageOrder = new ArticleImageOrderCollection();
        String filenameUnique1 = fixtureString();
        String filenameUnique2 = fixtureString();

        ImageOrder imageOrderUniqueArticle1 = new ImageOrder(fixtureString(), listOfFilename(filenameUnique1), imageOrderDuplicate1.getInhaltsklassifizierung(), imageOrderDuplicate1.getAufnahmeart(), null);
        ImageOrder imageOrderUniqueArticle2 = new ImageOrder(imageOrderDuplicate1.getPerspective(), listOfFilename(filenameUnique2), imageOrderDuplicate1.getInhaltsklassifizierung(), imageOrderDuplicate1.getAufnahmeart(), DetailFotografie.FARBFLECK);

        articleImageOrder.addImageOrderToArticle(imageOrderUniqueArticle1, articleNoFunction);
        articleImageOrder.addImageOrderToArticle(imageOrderUniqueArticle2, articleWithFunction);

        List<List<String>> duplicatesGroups = new ArrayList<>();
        duplicatesGroups.add(Arrays.asList(articleNoFunction.getArticleNumber(), articleWithFunction.getArticleNumber()));
        cut.mergeFilenamesToDuplicateImageOrders(duplicatesGroups, articleImageOrder);

        List<ImageOrder> act1 = articleImageOrder.getImageOrdersForArticle(articleNoFunction.getArticleNumber());
        List<ImageOrder> act2 = articleImageOrder.getImageOrdersForArticle(articleWithFunction.getArticleNumber());

        assertListOfImageOrdersContainsFilename(act1, filenameUnique1);
        assertListOfImageOrdersNotContainsFilename(act1, filenameUnique2);
        assertListOfImageOrdersContainsFilename(act2, filenameUnique2);
        assertListOfImageOrdersNotContainsFilename(act2, filenameUnique1);
    }

    @Test
    public void when_imageorderduplicatemerger_is_called_then_filenames_of_two_images_for_one_article_are_not_merged() {
        ArticleImageOrderCollection articleImageOrder = new ArticleImageOrderCollection();
        articleImageOrder.addImageOrderToArticle(imageOrderDuplicate1, articleNoFunction);
        articleImageOrder.addImageOrderToArticle(imageOrderDuplicate2, articleNoFunction);

        List<List<String>> duplicatesGroups = new ArrayList<>();
        duplicatesGroups.add(Arrays.asList(articleNoFunction.getArticleNumber(), articleWithFunction.getArticleNumber()));
        cut.mergeFilenamesToDuplicateImageOrders(duplicatesGroups, articleImageOrder);

        List<ImageOrder> act1 = articleImageOrder.getImageOrdersForArticle(articleNoFunction.getArticleNumber());
        for (ImageOrder imageOrder : act1) {
            if (imageOrder.getFilenames().contains(filenameDuplicate1)) {
                assertFalse(imageOrder.getFilenames().contains(filenameDuplicate2));

            } else {
                assertTrue(imageOrder.getFilenames().contains(filenameDuplicate2));
            }
        }
    }

    @Test
    public void when_imageorderduplicatemerger_is_called_then_duplicate_mirror_image_orders_with_same_attributes_should_not_be_merged_in_one_article() {
        ArticleImageOrderCollection articleImageOrder = new ArticleImageOrderCollection();
        articleImageOrder.addImageOrderToArticle(imageOrderDuplicate1, articleNoFunction);
        articleImageOrder.addImageOrderToArticle(imageOrderDuplicate2, articleNoFunction);

        List<List<String>> duplicatesGroups = new ArrayList<>();
        duplicatesGroups.add(Arrays.asList(articleNoFunction.getArticleNumber(), articleWithFunction.getArticleNumber()));
        cut.mergeFilenamesToDuplicateImageOrders(duplicatesGroups, articleImageOrder);

        List<ImageOrder> act1 = articleImageOrder.getImageOrdersForArticle(articleNoFunction.getArticleNumber());

        assertEquals(2, act1.size());

        for (ImageOrder imageOrder : act1) {
            if (imageOrder.getFilenames().contains(filenameDuplicate1)) {
                assertTrue(imageOrder.getFilenamesMirror().contains(filenameDuplicateMirror1));
                assertFalse(imageOrder.getFilenamesMirror().contains(filenameDuplicateMirror2));
            }
            if (imageOrder.getFilenames().contains(filenameDuplicate2)) {
                assertFalse(imageOrder.getFilenamesMirror().contains(filenameDuplicateMirror1));
                assertTrue(imageOrder.getFilenamesMirror().contains(filenameDuplicateMirror2));
            }
        }
    }
}
