package application.output.formats.csv;

public class InvalidCsvContent extends Exception {
    public InvalidCsvContent(String value) {
        super(value);
    }
}
