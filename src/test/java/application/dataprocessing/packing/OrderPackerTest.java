package application.dataprocessing.packing;

import core.ArticleImageOrderCollection;
import core.article.Article;
import core.article.ImageGroupCharacteristics;
import core.article.Materials;
import core.image.DetailFotografie;
import core.image.ImageOrder;
import core.order.ImageGroupOrder;
import core.order.ImageOrderGroupKey;
import core.order.Order;
import org.junit.Before;
import org.junit.Test;
import utils.UnitTest;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

public class OrderPackerTest extends UnitTest {
    private OrderPacker cut;

    private int getImageOrderCount(ImageGroupOrder imageGroupOrder) {
        int imageCount = 0;
        for (List<ImageOrder> imageOrderList : imageGroupOrder.getBasicModel().getMaterialsImageOrderListMap().imageOrderGroups.values())
            imageCount += imageOrderList.size();
        for (String function : imageGroupOrder.getFunctions())
            for (List<ImageOrder> imageOrderList : imageGroupOrder.getFunctionModel(function).getMaterialsImageOrderListMap().imageOrderGroups.values())
                imageCount += imageOrderList.size();

        return imageCount;
    }

    @Before
    public void setup() {
        cut = new OrderPacker();
    }

    @Test
    public void when_orderpacker_is_called_for_article_without_imageorder_then_no_exception_raises() {
        ArticleImageOrderCollection articleImageOrders = new ArticleImageOrderCollection();
        articleImageOrders.addArticle(createArticleForArticleNumber(""));
        Order act = cut.createOrder("", articleImageOrders);
        assertEquals(1, act.getImageGroupOrders().size());
    }

    @Test
    public void when_orderpacker_is_called_for_articles_with_different_styleids_then_it_returns_different_styleorders() {
        String styleA = fixtureString();
        String styleB = fixtureString();
        String styleC = fixtureString();
        Article articleStyleA1 = createArticleForStyle(styleA);
        Article articleStyleA2 = createArticleForStyle(styleA);
        Article articleStyleB1 = createArticleForStyle(styleB);
        Article articleStyleB2 = createArticleForStyle(styleB);
        Article articleStyleB3 = createArticleForStyle(styleB);
        Article articleStyleC1 = createArticleForStyle(styleC);

        Article[] articles = {articleStyleA1, articleStyleA2, articleStyleB1, articleStyleB2, articleStyleB3, articleStyleC1};

        ArticleImageOrderCollection articleImageOrders = new ArticleImageOrderCollection();

        for (Article article : articles) {
            articleImageOrders.addImageOrderToArticle(fixtureImageOrder(), article);
            articleImageOrders.addImageOrderToArticle(fixtureImageOrder(), article);
        }

        Collection<ImageGroupOrder> act = cut.createOrder("", articleImageOrders).getImageGroupOrders();
        assertEquals(3, act.size());
    }

    @Test
    public void when_orderpacker_is_called_for_articles_with_different_styleids_then_it_puts_image_orders_to_correct_styleorders() {
        final String styleA = "styleA";
        final String styleB = "styleB";
        final String styleC = "styleC";
        Article articleStyleA1 = createArticleForStyle(styleA);
        Article articleStyleA2 = createArticleForStyle(styleA);
        Article articleStyleB1 = createArticleForStyle(styleB);
        Article articleStyleB2 = createArticleForStyle(styleB);
        Article articleStyleB3 = createArticleForStyle(styleB);
        Article articleStyleC1 = createArticleForStyle(styleC);

        Article[] articles = {articleStyleA1, articleStyleA2, articleStyleB1, articleStyleB2, articleStyleB3, articleStyleC1};
        ArticleImageOrderCollection articleImageOrders = new ArticleImageOrderCollection();

        for (Article article : articles) {
            articleImageOrders.addImageOrderToArticle(fixtureImageOrder(), article);
            articleImageOrders.addImageOrderToArticle(fixtureImageOrder(), article);
        }

        List<ImageGroupOrder> act = cut.createOrder("", articleImageOrders).getImageGroupOrders();

        for (ImageGroupOrder imageGroupOrder : act) {
            switch (imageGroupOrder.getStyleId()) {
                case styleA:
                    assertEquals(4, getImageOrderCount(imageGroupOrder));
                    break;
                case styleB:
                    assertEquals(6, getImageOrderCount(imageGroupOrder));
                    break;
                case styleC:
                    assertEquals(2, getImageOrderCount(imageGroupOrder));
                    break;
                default:
                    fail("unknown styleId");
            }
        }
    }

    @Test
    public void when_orderpacker_is_called_then_the_basic_model_is_filled_with_nofunction_imageorders() {
        HashSet<String> functions = new HashSet<>();
        String function = fixtureString();
        String filenameNonFunction = fixtureString();
        String filenameFunction = fixtureString();
        functions.add(function);
        Article articleWithFunction = createArticleForFunction(functions);

        ArticleImageOrderCollection articleImageOrders = new ArticleImageOrderCollection();
        articleImageOrders.addImageOrderToArticle(new ImageOrder(fixtureString(), listOfFilename(filenameFunction), fixtureString(), fixtureString(), DetailFotografie.FUNKTION), articleWithFunction);
        articleImageOrders.addImageOrderToArticle(new ImageOrder(fixtureString(), listOfFilename(filenameNonFunction), fixtureString(), fixtureString(), null), articleWithFunction);

        ImageGroupOrder act = cut.createOrder("", articleImageOrders).getImageGroupOrders().get(0);

        assertNotNull(act.getBasicModel());
        List<ImageOrder> actBasicImageOrders = act.getBasicModel().getMaterialsImageOrderListMap().get(new ImageOrderGroupKey(articleWithFunction.getMaterials(), function));
        assertEquals(1, actBasicImageOrders.get(0).getFilenames().size());
        assertTrue(actBasicImageOrders.get(0).getFilenames().contains(filenameNonFunction));
        assertFalse(actBasicImageOrders.get(0).getFilenames().contains(filenameFunction));
    }

    @Test
    public void when_orderpacker_is_called_then_the_function_model_is_filled_with_function_imageorders() {
        HashSet<String> functions = new HashSet<>();
        String function = fixtureString();
        String filenameNonFunction = fixtureString();
        String filenameFunction = fixtureString();
        functions.add(function);
        Article articleWithFunction = createArticleForFunction(functions);


        ArticleImageOrderCollection articleImageOrders = new ArticleImageOrderCollection();
        articleImageOrders.addImageOrderToArticle(new ImageOrder(fixtureString(), listOfFilename(filenameFunction), fixtureString(), fixtureString(), DetailFotografie.FUNKTION), articleWithFunction);
        articleImageOrders.addImageOrderToArticle(new ImageOrder(fixtureString(), listOfFilename(filenameNonFunction), fixtureString(), fixtureString(), null), articleWithFunction);

        ImageGroupOrder act = cut.createOrder("", articleImageOrders).getImageGroupOrders().get(0);

        assertNotNull(act.getFunctionModel(function));
        List<ImageOrder> actImageOrdersForFunction = act.getFunctionModel(function).getMaterialsImageOrderListMap().get(new ImageOrderGroupKey(articleWithFunction.getMaterials(), function));
        assertEquals(1, actImageOrdersForFunction.get(0).getFilenames().size());
        assertTrue(actImageOrdersForFunction.get(0).getFilenames().contains(filenameFunction));
        assertFalse(actImageOrdersForFunction.get(0).getFilenames().contains(filenameNonFunction));
    }

    @Test
    public void when_orderpacker_is_called_for_articles_with_different_materials_then_all_models_relate_to_different_materials() {
        String filenameMaterialsA = fixtureString();
        String filenameMaterialsB = fixtureString();
        ImageGroupCharacteristics imageGroupCharacteristics = fixtureImageGroupCharacteristics();
        Article articleMaterialsA = createArticleForImageGroupCharacteristics(imageGroupCharacteristics);
        Article articleMaterialsB = createArticleForImageGroupCharacteristics(imageGroupCharacteristics);

        ImageOrder imageOrderForMaterialsA = new ImageOrder(fixtureString(), listOfFilename(filenameMaterialsA), fixtureString(), fixtureString(), null);
        ImageOrder imageOrderForMaterialsB = new ImageOrder(fixtureString(), listOfFilename(filenameMaterialsB), fixtureString(), fixtureString(), null);

        ArticleImageOrderCollection articleImageOrders = new ArticleImageOrderCollection();
        articleImageOrders.addImageOrderToArticle(imageOrderForMaterialsA, articleMaterialsA);
        articleImageOrders.addImageOrderToArticle(imageOrderForMaterialsB, articleMaterialsB);

        ImageGroupOrder act = cut.createOrder("", articleImageOrders).getImageGroupOrders().get(0);

        assertEquals(2, act.getBasicModel().getMaterialsImageOrderListMap().imageOrderGroups.size());

        List<ImageOrder> imageOrderListForMaterialsA = act.getBasicModel().getMaterialsImageOrderListMap().get(articleMaterialsA.getMaterials(), "");
        List<ImageOrder> imageOrderListForMaterialsB = act.getBasicModel().getMaterialsImageOrderListMap().get(articleMaterialsB.getMaterials(), "");

        for (ImageOrder imageOrderMaterialsA : imageOrderListForMaterialsA) {
            assertTrue(imageOrderMaterialsA.getFilenames().contains(filenameMaterialsA));
            assertFalse(imageOrderMaterialsA.getFilenames().contains(filenameMaterialsB));
        }
        for (ImageOrder imageOrderMaterialsB : imageOrderListForMaterialsB) {
            assertFalse(imageOrderMaterialsB.getFilenames().contains(filenameMaterialsA));
            assertTrue(imageOrderMaterialsB.getFilenames().contains(filenameMaterialsB));
        }
    }

    @Test
    public void when_orderpacker_is_called_for_articles_with_different_functions_then_functions_are_split_into_two_models() {
        Materials materials = fixtureMaterials();
        HashSet<String> functionList1 = new HashSet<>();
        HashSet<String> functionList2 = new HashSet<>();
        String function1 = fixtureString();
        String function2 = fixtureString();
        functionList1.add(function1);
        functionList2.add(function2);

        ArticleImageOrderCollection articleImageOrders = new ArticleImageOrderCollection();
        String filename1 = fixtureString();
        String filename2 = fixtureString();
        ImageOrder imageOrder1 = new ImageOrder(fixtureString(), listOfFilename(filename1), fixtureString(), fixtureString(), DetailFotografie.FUNKTION);
        ImageOrder imageOrder2 = new ImageOrder(fixtureString(), listOfFilename(filename2), fixtureString(), fixtureString(), DetailFotografie.FUNKTION);
        ImageGroupCharacteristics imageGroupCharacteristics = fixtureImageGroupCharacteristics();
        articleImageOrders.addImageOrderToArticle(imageOrder1, new Article(fixtureString(), materials, functionList1, false, "", imageGroupCharacteristics));
        articleImageOrders.addImageOrderToArticle(imageOrder2, new Article(fixtureString(), materials, functionList2, false, "", imageGroupCharacteristics));

        ImageGroupOrder act = cut.createOrder("", articleImageOrders).getImageGroupOrders().get(0);

        assertEquals(2, act.getFunctions().size());
        List<ImageOrder> actImageList1 = act.getFunctionModel(function1).getMaterialsImageOrderListMap().get(materials, function1);
        List<ImageOrder> actImageList2 = act.getFunctionModel(function2).getMaterialsImageOrderListMap().get(materials, function2);
        assertTrue(actImageList1.get(0).getFilenames().contains(filename1));
        assertFalse(actImageList1.get(0).getFilenames().contains(filename2));
        assertFalse(actImageList2.get(0).getFilenames().contains(filename1));
        assertTrue(actImageList2.get(0).getFilenames().contains(filename2));
    }

    @Test
    public void when_orderpacker_is_called_for_function_and_nonfunction_article_then_style_has_baisc_and_function_model() {
        Materials materials = fixtureMaterials();
        HashSet<String> functionList = new HashSet<>();
        String function1 = fixtureString();
        functionList.add(function1);
        String articleWithFunctionNumber = fixtureString();
        String articleNumber = fixtureString();
        ImageGroupCharacteristics imageGroupCharacteristics = fixtureImageGroupCharacteristics();
        Article article = new Article(articleNumber, materials, null, false, "", imageGroupCharacteristics);
        Article articleWithFunction = new Article(articleWithFunctionNumber, materials, functionList, false, "", imageGroupCharacteristics);

        ArticleImageOrderCollection articleImageOrders = new ArticleImageOrderCollection();
        String filename1 = fixtureString();
        String filename2 = fixtureString();
        ImageOrder imageOrderFunction = new ImageOrder(fixtureString(), listOfFilename(filename1), fixtureString(), fixtureString(), DetailFotografie.FUNKTION);
        ImageOrder imageOrder = new ImageOrder(fixtureString(), listOfFilename(filename2), fixtureString(), fixtureString(), null);
        articleImageOrders.addImageOrderToArticle(imageOrderFunction, articleWithFunction);
        articleImageOrders.addImageOrderToArticle(imageOrder, article);

        ImageGroupOrder act = cut.createOrder("", articleImageOrders).getImageGroupOrders().get(0);

        assertEquals(1, act.getFunctions().size());
    }

    @Test
    public void when_orderpacker_is_called_for_an_article_with_two_functions_then_functions_are_merged_to_one_model() {
        HashSet<String> functions = new HashSet<>();
        functions.add(fixtureString());
        functions.add(fixtureString());

        Article articleWithFunction = createArticleForFunction(functions);

        ArticleImageOrderCollection articleImageOrders = new ArticleImageOrderCollection();
        ImageOrder imageOrderForFunction = new ImageOrder(fixtureString(), listOfFilename(fixtureString()), fixtureString(), fixtureString(), DetailFotografie.FUNKTION);
        articleImageOrders.addImageOrderToArticle(imageOrderForFunction, articleWithFunction);

        ImageGroupOrder act = cut.createOrder("", articleImageOrders).getImageGroupOrders().get(0);

        assertEquals(1, act.getFunctions().size());
    }

    @Test
    public void when_orderpacker_is_called_for_articles_with_subsets_of_their_functions_then_function_combinations_get_their_own_model() {
        Materials materials = fixtureMaterials();
        HashSet<String> functionListA = new HashSet<>();
        HashSet<String> functionListAB = new HashSet<>();
        HashSet<String> functionListB = new HashSet<>();
        String functionA = fixtureString();
        String functionB = fixtureString();
        functionListA.add(functionA);
        functionListB.add(functionB);
        functionListAB.add(functionA);
        functionListAB.add(functionB);
        ImageGroupCharacteristics imageGroupCharacteristics = fixtureImageGroupCharacteristics();
        Article articleFunctionA = new Article(fixtureString(), materials, functionListA, false, "", imageGroupCharacteristics);
        Article articleFunctionB = new Article(fixtureString(), materials, functionListB, false, "", imageGroupCharacteristics);
        Article articleFunctionAB = new Article(fixtureString(), materials, functionListAB, false, "", imageGroupCharacteristics);

        ArticleImageOrderCollection articleImageOrders = new ArticleImageOrderCollection();
        String filenameA = fixtureString();
        String filenameB = fixtureString();
        String filenameAB = fixtureString();
        ImageOrder imageOrderA = new ImageOrder(fixtureString(), listOfFilename(filenameA), fixtureString(), fixtureString(), DetailFotografie.FUNKTION);
        ImageOrder imageOrderB = new ImageOrder(fixtureString(), listOfFilename(filenameB), fixtureString(), fixtureString(), DetailFotografie.FUNKTION);
        ImageOrder imageOrderAB = new ImageOrder(fixtureString(), listOfFilename(filenameAB), fixtureString(), fixtureString(), DetailFotografie.FUNKTION);
        articleImageOrders.addImageOrderToArticle(imageOrderA, articleFunctionA);
        articleImageOrders.addImageOrderToArticle(imageOrderB, articleFunctionB);
        articleImageOrders.addImageOrderToArticle(imageOrderAB, articleFunctionAB);

        ImageGroupOrder act = cut.createOrder("", articleImageOrders).getImageGroupOrders().get(0);

        assertEquals(3, act.getFunctions().size());
    }

    @Test
    public void when_orderpacker_is_called_for_article_functions_then_image_orders_of_sortimentsdetail_are_added_to_basic_model() {
        HashSet<String> functionListA = new HashSet<>();
        String functionA = fixtureString();
        functionListA.add(functionA);
        Article articleFunctionA = createArticleForFunction(functionListA);

        ArticleImageOrderCollection articleImageOrders = new ArticleImageOrderCollection();
        String filenameA = fixtureString();
        ImageOrder imageOrder1 = new ImageOrder(fixtureString(), listOfFilename(filenameA), fixtureString(), fixtureString(), DetailFotografie.SORTIMENTSDETAIL);
        ImageOrder imageOrder2 = new ImageOrder(fixtureString(), listOfFilename(filenameA), fixtureString(), fixtureString(), DetailFotografie.FUNKTION);
        articleImageOrders.addImageOrderToArticle(imageOrder1, articleFunctionA);
        articleImageOrders.addImageOrderToArticle(imageOrder2, articleFunctionA);

        ImageGroupOrder act = cut.createOrder("", articleImageOrders).getImageGroupOrders().get(0);

        List<ImageOrder> imageOrdersBasic = act.getBasicModel().getMaterialsImageOrderListMap().get(articleFunctionA.getMaterials(), functionA);
        List<ImageOrder> imageOrdersFunction = act.getFunctionModel(functionA).getMaterialsImageOrderListMap().get(articleFunctionA.getMaterials(), functionA);
        assertEquals(1, imageOrdersBasic.size());
        assertEquals(1, imageOrdersFunction.size());

        assertEquals(DetailFotografie.SORTIMENTSDETAIL, imageOrdersBasic.get(0).getDetailfotografie());
        assertEquals(DetailFotografie.FUNKTION, imageOrdersFunction.get(0).getDetailfotografie());
    }

    @Test
    public void when_orderpacker_is_called_for_articles_with_same_image_group_then_one_style_order_is_returned() {
        ImageGroupCharacteristics imageGroupCharacteristics = fixtureImageGroupCharacteristics();
        Article article1 = createArticleForImageGroupCharacteristics(imageGroupCharacteristics);
        Article article2 = createArticleForImageGroupCharacteristics(imageGroupCharacteristics);

        ArticleImageOrderCollection articleImageOrders = new ArticleImageOrderCollection();
        articleImageOrders.addImageOrderToArticle(fixtureImageOrder(), article1);
        articleImageOrders.addImageOrderToArticle(fixtureImageOrder(), article2);

        List<ImageGroupOrder> act = cut.createOrder("", articleImageOrders).getImageGroupOrders();
        assertEquals(1, act.size());
    }

    @Test
    public void when_orderpacker_is_called_for_articles_with_different_image_group_then_two_style_order_is_returned() {
        Article article1 = createArticleForImageGroupCharacteristics(fixtureImageGroupCharacteristics());
        Article article2 = createArticleForImageGroupCharacteristics(fixtureImageGroupCharacteristics());

        ArticleImageOrderCollection articleImageOrders = new ArticleImageOrderCollection();
        articleImageOrders.addImageOrderToArticle(fixtureImageOrder(), article1);
        articleImageOrders.addImageOrderToArticle(fixtureImageOrder(), article2);

        List<ImageGroupOrder> act = cut.createOrder("", articleImageOrders).getImageGroupOrders();

        assertEquals(2, act.size());
    }
}
