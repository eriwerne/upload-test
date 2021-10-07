package application.validation.exceptions;

import core.article.Article;

import java.util.List;

public abstract class ContainerIdValidationException extends Exception {

    public ContainerIdValidationException(String message, List<String> containerIds, List<Article> articles) {
        super(message + ": \n" + containerIds + "\n" + "Please check the following articles for their relevant attributes like functions and materials or articles that share the same attribute values:\n" + articles);
    }
}
