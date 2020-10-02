package application.validation.exceptions;

import java.util.List;

public class DuplicateContainerIdsException extends ContainerIdValidationException {
    public DuplicateContainerIdsException(List<String> containerIds) {
        super("Container ids have duplicates in result", containerIds);
    }
}
