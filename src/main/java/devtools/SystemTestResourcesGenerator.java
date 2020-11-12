package devtools;

import application.ProcessMediator;
import application.exceptions.ApplicationFailed;
import application.output.PersisterFailure;
import application.reading.exception.OrderNotFound;
import application.reading.exception.ResourceFailure;
import application.reading.orderreader.OrderReader;
import infrastructure.filesystem.FilePersister;
import infrastructure.m2.M2;
import infrastructure.mmp.Mmp;

import java.io.File;
import java.util.List;

import static java.lang.String.format;

public class SystemTestResourcesGenerator {

    private static final String orderNumber = System.getenv("ORDER_NUMBER");
    private static final FilePersister filePersister = new FilePersister();
    private static String systemTestResourcesFolder;

    public static void main(String[] args) throws PersisterFailure, ResourceFailure, OrderNotFound, ApplicationFailed {
        String resourcesFolder = new File("src/test/resources/").getAbsolutePath();
        systemTestResourcesFolder = format("%s/systemtest/real_orders_from_live_system/%s", resourcesFolder, orderNumber);

        downloadOrder();
        downloadArticles();
        createExpectationFiles();
    }

    private static void downloadOrder() throws ResourceFailure, PersisterFailure {
        String orderResourcesFolder = format("%s/api/mmp/imageorders", systemTestResourcesFolder);
        String orderJson = new Mmp().getImageOrdersForOrderNumber(orderNumber);
        filePersister.persistString(orderResourcesFolder, orderNumber + ".json", orderJson);
    }

    private static void downloadArticles() throws OrderNotFound, ResourceFailure, PersisterFailure {
        String articleResourcesFolder = format("%s/api/m2/articles", systemTestResourcesFolder);
        List<String> articleNumbers = new OrderReader().readArticleNumbersInOrder(orderNumber);
        for (String articleNumber : articleNumbers) {
            String articleJson = new M2().getArticleString(articleNumber);
            filePersister.persistString(articleResourcesFolder, articleNumber + ".json", articleJson);
        }
    }

    private static void createExpectationFiles() throws ApplicationFailed {
        new ProcessMediator().runOrderNumber(orderNumber, "exp", systemTestResourcesFolder + "/expectation");
    }
}
