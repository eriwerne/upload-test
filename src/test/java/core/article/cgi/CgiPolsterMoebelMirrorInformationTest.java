package core.article.cgi;

import core.article.exceptions.UnknownMirrorInformation;
import org.junit.Test;

import static org.junit.Assert.*;

public class CgiPolsterMoebelMirrorInformationTest {
    @Test(expected = UnknownMirrorInformation.class)
    public void when_mirror_information_is_created_with_invalid_direction_then_exception_is_thrown() throws UnknownMirrorInformation {
        new CgiPolsterMoebelMirrorInformation("Schenkel xy");
    }

    @Test(expected = UnknownMirrorInformation.class)
    public void when_mirror_information_is_created_with_invalid_component_then_exception_is_thrown() throws UnknownMirrorInformation {
        new CgiPolsterMoebelMirrorInformation("beidseitig xy");
    }

    @Test
    public void when_mirror_information_contains_valid_mirror_then_mirror_is_returned() throws UnknownMirrorInformation {
        CgiPolsterMoebelMirrorInformation cut = new CgiPolsterMoebelMirrorInformation("Longchair rechts");
        assertTrue(cut.isMirror());
    }

    @Test
    public void when_mirror_information_contains_invalid_mirror_then_mirror_is_returned() throws UnknownMirrorInformation {
        CgiPolsterMoebelMirrorInformation cut = new CgiPolsterMoebelMirrorInformation("Ottomane links");
        assertFalse(cut.isMirror());
    }
}
