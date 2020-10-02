package application.exceptions;

public class ApplicationFailed extends Exception {
    public ApplicationFailed(Exception e) {
        super(e);
        e.printStackTrace();
    }
}
