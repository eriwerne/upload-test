package core.exceptions;

public class ArticleMissing extends RuntimeException {
    public ArticleMissing(String articleNumber) {
        super("Article not found: " + articleNumber);
    }
}
