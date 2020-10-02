package application.reading.exception;

public class ArticleNotFound extends Exception {
    public ArticleNotFound(String articleNumber) {
        super("Article " + articleNumber + " was not found");
    }

}
