package application.output;

public class OutputFailed extends Exception {
    public OutputFailed(Exception e) {
        super(e);
    }
}
