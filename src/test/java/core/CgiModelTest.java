package core;

import core.article.Materials;
import core.image.ImageOrder;
import core.order.CgiModel;
import core.order.ImageOrderGroupKey;
import org.junit.Before;
import org.junit.Test;
import utils.UnitTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CgiModelTest extends UnitTest {
    private CgiModel cut;

    @Before
    public void setup() {
        cut = new CgiModel();
    }

    @Test
    public void when_duplicate_imageorders_with_different_order_of_filenames_are_appended_then_they_are_merged() {
        ArrayList<String> filenames = new ArrayList<>();
        String filename1 = fixtureString();
        String filename2 = fixtureString();
        filenames.add(filename1);
        filenames.add(filename2);
        ArrayList<String> filenamesReverse = new ArrayList<>();
        filenamesReverse.add(filename2);
        filenamesReverse.add(filename1);
        String perspective = fixtureString();
        String inhaltsklassifizierung = fixtureString();
        String aufnahmeart = fixtureString();
        ImageOrder imageOrder1 = new ImageOrder(perspective, filenames, inhaltsklassifizierung, aufnahmeart, null);
        ImageOrder imageOrder2 = new ImageOrder(perspective, filenamesReverse, inhaltsklassifizierung, aufnahmeart, null);

        Materials materials = fixtureMaterials();
        cut.appendImageOrdersToMaterials(imageOrder1, materials, "");
        cut.appendImageOrdersToMaterials(imageOrder2, materials, "");
        List<ImageOrder> act = cut.getMaterialsImageOrderListMap().get(materials, "");

        assertEquals(1, act.size());
    }
}
