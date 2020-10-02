package application.reading.imageorderreader;

import application.output.Persister;
import application.output.PersisterFailure;
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
    @Inject
    Persister persister;
    private HashMap<String, List<ImageOrder>> responseArticleImageOrderMap = new HashMap<>();
    private final String downloadFolder = "cache/imageorders/";

    public ImageOrderReader() {
        DaggerImageOrderReaderInjection.builder().build().inject(this);
    }

    public void readImageOrdersForOrderNumber(String orderNumber) throws OrderNotFound, ResourceFailure, PersisterFailure {
        if (!persister.pathExists(downloadFolder, orderNumber)) {
            responseArticleImageOrderMap = imageOrderReaderSource.readImageOrdersForOrderNumber(orderNumber);
            persister.persistObject(downloadFolder, orderNumber, responseArticleImageOrderMap);
        } else {
            //noinspection unchecked
            responseArticleImageOrderMap = (HashMap<String, List<ImageOrder>>) persister.getObject(downloadFolder, orderNumber);
        }
    }

    public List<ImageOrder> returnImageOrdersForArticle(String articleNumber) throws ArticleNotFound {
        if (responseArticleImageOrderMap.containsKey(articleNumber))
            return responseArticleImageOrderMap.get(articleNumber);
        else
            throw new ArticleNotFound(articleNumber);
    }
}
