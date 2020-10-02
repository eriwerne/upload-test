package infrastructure.mmp.mapping;

import core.image.DetailFotografie;
import core.image.ImageOrder;
import utils.ResourceFileReader;
import infrastructure.mmp.schema.MmpOrder;
import org.junit.Before;
import org.junit.Test;
import utils.UnitTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class MmpImageOrderMapperTest extends UnitTest {
    private MmpImageOrderMapper cut;
    private final String articleNumber1 = "425854";
    private final String containerId = "1000002869";

    private String getJsonFromFile(String filename) {
        try {
            return ResourceFileReader.readResource("imageorder/mmpmapping/" + filename + ".json");
        } catch (
                IOException e) {
            throw new RuntimeException();
        }
    }

    private HashMap<String, List<ImageOrder>> getImageOrdersFromFile(String filename) {
        return cut.mapFromMmpObject(cut.mapJsonToMmpObject(getJsonFromFile(filename)));
    }

    private MmpOrder getMmpOrderFromFilename(String filename) {
        return cut.mapJsonToMmpObject(getJsonFromFile(filename));
    }

    @Before
    public void setup() {
        cut = new MmpImageOrderMapper();
    }

    @Test
    public void when_imageorder_maps_to_mmpobject_then_articlelist_is_set() {
        MmpOrder act = getMmpOrderFromFilename("single_article_single_container");
        assertEquals(1, act.getArticleList().length);
    }

    @Test
    public void when_imageorder_maps_to_mmpobject_then_articlenumber_is_set() {
        MmpOrder act = getMmpOrderFromFilename("single_article_single_container");
        assertEquals(articleNumber1, act.getArticleList()[0].getArticleNumber());
    }

    @Test
    public void when_imageorder_maps_to_mmpobject_then_containerlist_is_set() {
        MmpOrder act = getMmpOrderFromFilename("single_article_single_container");
        assertEquals(1, act.getArticleList()[0].getContainerList().length);
    }

    @Test
    public void when_imageorder_maps_to_mmpobject_then_containerid_is_set() {
        MmpOrder act = getMmpOrderFromFilename("single_article_single_container");
        assertEquals(containerId, act.getArticleList()[0].getContainerList()[0].getContainerId());
    }

    @Test
    public void when_imageorder_maps_to_mmpobject_then_ansicht_is_set() {
        MmpOrder act = getMmpOrderFromFilename("single_article_single_container");
        String ansicht = "Vorderansicht";
        assertEquals(ansicht, act.getArticleList()[0].getContainerList()[0].getAnsicht());
    }

    @Test
    public void when_imageorder_maps_to_mmpobject_then_inhaltsklassifizierung_is_set() {
        MmpOrder act = getMmpOrderFromFilename("single_article_single_container");
        String inhaltsklassifizierung = "Produktbild";
        assertEquals(inhaltsklassifizierung, act.getArticleList()[0].getContainerList()[0].getInhaltsklassifizierung());
    }

    @Test
    public void when_imageorder_maps_to_mmpobject_then_aufnahmeart_is_set() {
        MmpOrder act = getMmpOrderFromFilename("single_article_single_container");
        String aufnahmeart = "alleinstehend";
        assertEquals(aufnahmeart, act.getArticleList()[0].getContainerList()[0].getAufnahmeart());
    }

    @Test
    public void when_imageorder_maps_to_mmpobject_then_detailfotografie_is_set() {
        MmpOrder act = getMmpOrderFromFilename("single_article_single_container");
        String detailfotografie = "Digitales Stoffmuster";
        assertEquals(detailfotografie, act.getArticleList()[0].getContainerList()[0].getDetailfotografie());
    }

    @Test
    public void when_imageorder_mapper_is_called_for_one_article_then_result_has_one_entry() {
        HashMap<String, List<ImageOrder>> act = getImageOrdersFromFile("single_article_single_container");
        assertEquals(1, act.size());
    }

    @Test
    public void when_imageorder_mapper_is_called_for_two_article_then_result_has_two_entries() {
        String filename_two_articles = "two_articles";
        HashMap<String, List<ImageOrder>> act = getImageOrdersFromFile(filename_two_articles);
        assertEquals(2, act.size());
    }

    @Test
    public void when_imageorder_mapper_is_called_for_one_article_wtih_two_imageorders_then_result_contains_two_imageOrders() {
        String filename_single_article_two_container = "single_article_two_container";
        HashMap<String, List<ImageOrder>> act = getImageOrdersFromFile(filename_single_article_two_container);
        assertEquals(2, act.get(articleNumber1).size());
    }

    @Test
    public void when_imageorder_mapper_is_called_for_one_article_then_result_contains_containerId() {
        HashMap<String, List<ImageOrder>> act = getImageOrdersFromFile("single_article_single_container");
        assertTrue(act.get(articleNumber1).get(0).getFilenames().contains(containerId + ".jpg"));
    }

    @Test
    public void when_imageorder_mapper_is_called_for_one_article_then_result_contains_detailfotografie() {
        HashMap<String, List<ImageOrder>> act = getImageOrdersFromFile("single_article_single_container");
        assertEquals(DetailFotografie.DIGITALESSTOFFMUSTER, act.get(articleNumber1).get(0).getDetailfotografie());
    }

    @Test
    public void when_imageorder_mapper_is_called_then_imageorders_of_with_combination_are_rejected() {
        String filename_contains_combination = "contains_combination";
        HashMap<String, List<ImageOrder>> act = getImageOrdersFromFile(filename_contains_combination);
        assertEquals(1, act.get(articleNumber1).size());
    }

    @Test
    public void when_imageorder_mapper_is_called_then_articles_with_combination_images_are_rejected() {
        String filename_single_article_single_combination_container = "single_article_single_combination_container";
        HashMap<String, List<ImageOrder>> act = getImageOrdersFromFile(filename_single_article_single_combination_container);
        assertEquals(0, act.size());
    }

    @Test
    public void when_imageorder_mapper_is_called_then_null_values_in_imageorder_attributes_can_be_handled() {
        String filename_single_article_single_null_container = "single_article_single_null_container";
        HashMap<String, List<ImageOrder>> act = getImageOrdersFromFile(filename_single_article_single_null_container);
        assertEquals(1, act.size());
    }
}
