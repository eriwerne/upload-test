package application.output.formats;

import application.output.formats.csv.CsvFormat;
import application.output.formats.json.JsonFormat;

public enum OutputFormatType {
    JSON, CSV;

    public String getFileExtension() {
        switch (this) {
            case JSON:
                return ".json";
            case CSV:
                return ".csv";
        }
        return null;
    }

    public OutputFormat getOutputFormatting() {
        switch (this) {
            case JSON:
                return new JsonFormat();
            case CSV:
                return new CsvFormat();
        }
        return null;
    }
}
