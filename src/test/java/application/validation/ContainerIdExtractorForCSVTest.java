package application.validation;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ContainerIdExtractorForCSVTest {
    private ContainerIdExtractorForCSV cut;

    @Test
    public void when_container_id_extractor_is_executed_for_string_with_no_image_filename_then_empty_array_is_returned() {
        String string = "no image";
        cut = new ContainerIdExtractorForCSV(string);
        List<String> act = cut.readContainerIdsInObject();
        assertEquals(0, act.size());
    }

    @Test
    public void when_container_id_extractor_is_executed_for_string_that_is_a_image_filename_then_filename_is_returned() {
        String filename = "14628595.jpg";
        cut = new ContainerIdExtractorForCSV(filename);
        List<String> act = cut.readContainerIdsInObject();
        assertEquals(1, act.size());
        assertTrue(act.contains(filename));
    }

    @Test
    public void when_container_id_extractor_is_executed_for_string_that_contains_two_image_filenames_then_both_filenames_are_returned() {
        String filename1 = "14628595.jpg";
        String filename2 = "14628907.jpg";
        String string = filename1 + ", " + filename2;
        cut = new ContainerIdExtractorForCSV(string);
        List<String> act = cut.readContainerIdsInObject();
        assertEquals(2, act.size());
        assertTrue(act.contains(filename1));
        assertTrue(act.contains(filename2));
    }

    @Test
    public void when_container_id_extractor_is_executed_for_csv_line_then_all_filenames_are_returned() {
        String filename1 = "14628595.jpg";
        String filename2 = "14628907.jpg";
        String filename3 = "14629195.jpg";
        String filename4 = "14629459.jpg";
        String string = "107452970; steinpol_altara_nubuck_bordeaux_63; ; ; alleinstehend; Produktbild; Vorderansicht leicht seitlich rechts; [" + filename1 + ", " + filename2 + "]; [" + filename3 + ", " + filename4 + "]; ";
        cut = new ContainerIdExtractorForCSV(string);
        List<String> act = cut.readContainerIdsInObject();
        assertEquals(4, act.size());
        assertTrue(act.contains(filename1));
        assertTrue(act.contains(filename2));
        assertTrue(act.contains(filename3));
        assertTrue(act.contains(filename4));
    }
}
