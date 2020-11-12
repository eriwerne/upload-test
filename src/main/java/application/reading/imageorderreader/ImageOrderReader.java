package application.reading.imageorderreader;

import application.reading.exception.ArticleNotFound;
import application.reading.exception.OrderNotFound;
import application.reading.exception.ResourceFailure;
import core.image.ImageOrder;
import injection.DaggerImageOrderReaderInjection;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;

public class ImageOrderReader {
    @Inject
    ImageOrderReaderSource imageOrderReaderSource;
    private HashMap<String, List<ImageOrder>> responseArticleImageOrderMap = new HashMap<>();

    public ImageOrderReader() {
        DaggerImageOrderReaderInjection.builder().build().inject(this);
    }

    public void readImageOrdersForOrderNumber(String orderNumber) throws OrderNotFound, ResourceFailure {
        responseArticleImageOrderMap = imageOrderReaderSource.readImageOrdersForOrderNumber(orderNumber);
    }

    public List<ImageOrder> returnImageOrdersForArticle(String articleNumber) throws ArticleNotFound {
        if (responseArticleImageOrderMap.containsKey(articleNumber))
            return responseArticleImageOrderMap.get(articleNumber);
        else
            throw new ArticleNotFound(articleNumber);
    }
}
