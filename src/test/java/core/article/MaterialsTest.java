package core.article;

import org.junit.Before;
import org.junit.Test;
import utils.UnitTest;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class MaterialsTest extends UnitTest {
    private Materials material1;
    private Materials material2;

    @Before
    public void setup() {
        material1 = new Materials(
                "materialCode",
                new String[]{"furnitureCoverMaterial1", "furnitureCoverMaterial2"},
                new String[]{"furnitureCover1", "furnitureCover2"},
                new String[]{"feetMaterial1", "feetMaterial2"},
                new String[]{"feetColor1", "feetColor2"}
        );
        material2 = new Materials(
                "materialCode",
                new String[]{"furnitureCoverMaterial1", "furnitureCoverMaterial2"},
                new String[]{"furnitureCover1", "furnitureCover2"},
                new String[]{"feetMaterial1", "feetMaterial2"},
                new String[]{"feetColor1", "feetColor2"}
        );
    }
    @Test
    public void when_two_identical_materials_are_compared_then_both_have_the_same_hashcode() {
        assertEquals(material1.hashCode(), material2.hashCode());
    }

    @Test
    public void when_two_identical_materials_are_compared_then_both_are_equal() {
        assertEquals(material1, material2);
    }

    @Test
    public void when_two_identical_materials_are_converted_to_string_then_both_strings_are_equal() {
        assertEquals(material1.toString(), material2.toString());
    }

    @Test
    public void when_two_identical_materials_are_put_into_a_hashset_then_only_one_instance_remains() {
        HashSet<Materials> materialHashSet = new HashSet<>();

        materialHashSet.add(material1);
        materialHashSet.add(material2);
        assertEquals(1, materialHashSet.size());
    }
}
