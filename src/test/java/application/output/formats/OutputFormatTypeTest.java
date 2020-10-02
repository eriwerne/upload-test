package application.output.formats;

import org.junit.Test;
import utils.UnitTest;

import static org.junit.Assert.*;

public class OutputFormatTypeTest extends UnitTest {
    @Test
    public void all_format_types_return_file_extensions() {
        for (OutputFormatType type : OutputFormatType.values())
            assertNotNull(type.getFileExtension());
    }
    @Test
    public void all_format_types_return_output_formatting() {
        for (OutputFormatType type : OutputFormatType.values())
            assertNotNull(type.getOutputFormatting());
    }
}
