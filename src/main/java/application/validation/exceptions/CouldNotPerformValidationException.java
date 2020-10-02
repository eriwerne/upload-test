package application.validation.exceptions;

public class CouldNotPerformValidationException extends Throwable {
    public CouldNotPerformValidationException(Exception e) {
        super(e);
    }
}
