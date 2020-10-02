package application.validation.exceptions;

import java.util.List;

public abstract class ContainerIdValidationException extends Exception {

    public ContainerIdValidationException(String message, List<String> containerIds) {
        super(message + ": \n" + containerIds);
    }
}
