package application.validation.exceptions;

import core.article.Article;

import java.util.List;

public class MissingContainerIdsException extends ContainerIdValidationException {
    public MissingContainerIdsException(List<String> containerIds, List<Article> articles) {
        super("Container ids were missing in result", containerIds, articles);
    }
}
