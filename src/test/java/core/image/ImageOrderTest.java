package core.image;

import org.junit.Test;
import utils.UnitTest;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ImageOrderTest extends UnitTest {
    @Test
    public void when_two_identical_imageorders_are_compared_then_they_are_equal() {
        String perspective = fixtureString();
        String inhaltsklassifizierung = fixtureString();
        String aufnahmeart = fixtureString();
        DetailFotografie markendetail = DetailFotografie.MARKENDETAIL;
        ImageOrder imageOrder1 = new ImageOrder(perspective, fixtureList(), inhaltsklassifizierung, aufnahmeart, markendetail);
        ImageOrder imageOrder2 = new ImageOrder(perspective, fixtureList(), inhaltsklassifizierung, aufnahmeart, markendetail);
        assertEquals(imageOrder1, imageOrder2);
    }
    @Test
    public void when_imageorders_with_different_detailfotografie_are_compared_then_they_are_not_equal() {
        String perspective = fixtureString();
        String inhaltsklassifizierung = fixtureString();
        String aufnahmeart = fixtureString();
        ImageOrder imageOrder1 = new ImageOrder(perspective, fixtureList(), inhaltsklassifizierung, aufnahmeart, DetailFotografie.NULL);
        ImageOrder imageOrder2 = new ImageOrder(perspective, fixtureList(), inhaltsklassifizierung, aufnahmeart, DetailFotografie.MARKENDETAIL);
        assertNotEquals(imageOrder1, imageOrder2);
    }
}
