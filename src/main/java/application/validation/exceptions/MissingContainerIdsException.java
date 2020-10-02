package application.validation.exceptions;

import java.util.List;

public class MissingContainerIdsException extends ContainerIdValidationException {
    public MissingContainerIdsException(List<String> containerIds) {
        super("Container ids were missing in result", containerIds);
    }
}
