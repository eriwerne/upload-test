package application.validation.exceptions;

import core.article.Article;

import java.util.List;

public class TooManyContainerIdsException extends ContainerIdValidationException {
    public TooManyContainerIdsException(List<String> containerIds, List<Article> articles) {
        super("Container ids in result were not requested in order", containerIds, articles);
    }
}
