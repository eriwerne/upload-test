package application.validation.exceptions;

import java.util.List;

public class TooManyContainerIdsException extends ContainerIdValidationException {
    public TooManyContainerIdsException(List<String> containerIds) {
        super("Container ids in result were not requested in order", containerIds);
    }
}
