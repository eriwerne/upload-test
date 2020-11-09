package devtools;

import application.output.PersisterFailure;
import application.reading.exception.ArticleNotFound;
import application.reading.exception.OrderNotFound;
import application.reading.exception.ResourceFailure;
import application.reading.orderreader.OrderReader;
import core.article.exceptions.InvalidArticleData;
import devtools.apiqualityinspection.ArticleFunctionQualityInspector;
import devtools.apiqualityinspection.ArticleMaterialQualityInspector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ArticleQualityInspector {
    public static void main(String[] args) throws ResourceFailure, OrderNotFound, InvalidArticleData, ArticleNotFound, PersisterFailure {
        String orderNumber = System.getenv("ORDER_NUMBER");
        List<String> articleNumbers = new OrderReader().readArticleNumbersInOrder(orderNumber);

        Map<String, String> invalidArticleMaterialId = getMaterialQuality(articleNumbers);
        HashMap<String, HashMap<String, List<String>>> functionStyleArticles = getFunctionQuality(articleNumbers);

        showHead(articleNumbers);
        showMaterialQuality(invalidArticleMaterialId);
        showFunctionQuality(functionStyleArticles);
    }

    private static HashMap<String, HashMap<String, List<String>>> getFunctionQuality(List<String> articleNumbers) throws ResourceFailure, InvalidArticleData, ArticleNotFound, PersisterFailure {
        ArticleFunctionQualityInspector functionQualityChecker = new ArticleFunctionQualityInspector();
        return functionQualityChecker.getArticleFunctions(articleNumbers);
    }

    private static Map<String, String> getMaterialQuality(List<String> articleNumbers) throws ResourceFailure, InvalidArticleData, ArticleNotFound, PersisterFailure {
        ArticleMaterialQualityInspector materialQualityChecker = new ArticleMaterialQualityInspector();
        return materialQualityChecker.getInvalidArticleMaterialId(articleNumbers);
    }

    private static void showHead(List<String> articleNumbers) {
        System.out.println();
        System.out.println("Checking article numbers:");
        System.out.println(articleNumbers);
    }

    private static void showFunctionQuality(HashMap<String, HashMap<String, List<String>>> styleArticlesForFunctions) {
        System.out.println("Article functions:");
        for (String function : styleArticlesForFunctions.keySet()) {
            System.out.println(function);
            HashMap<String, List<String>> stylesAndArticlesForFunction = styleArticlesForFunctions.get(function);
            for (String style : stylesAndArticlesForFunction.keySet()) {
                System.out.println("\tStyle: " + style);
                System.out.println("\tArticle:" + stylesAndArticlesForFunction.get(style));
            }
        }
    }

    private static void showMaterialQuality(Map<String, String> articleNumbersWithInvalidMaterial) {
        System.out.println("Article numbers with invalid material:");
        System.out.println(articleNumbersWithInvalidMaterial);
    }

}
