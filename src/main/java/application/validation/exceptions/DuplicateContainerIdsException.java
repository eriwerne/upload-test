package application.validation.exceptions;

import core.article.Article;

import java.util.List;

public class DuplicateContainerIdsException extends ContainerIdValidationException {
    public DuplicateContainerIdsException(List<String> containerIds, List<Article> articles) {
        super("Container ids have duplicates in result", containerIds, articles);
    }
}
