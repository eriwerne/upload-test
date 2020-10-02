package application.output;

public class PersisterFailure extends Exception {
    public PersisterFailure(Exception e) {
        super(e);
    }
}
