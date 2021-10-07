package core;

import core.article.Article;
import core.exceptions.ArticleMissing;
import core.article.ImageGroupCharacteristics;
import core.article.Materials;
import core.image.ImageOrder;
import core.image.exceptions.InvalidDetailFotografie;

import java.util.*;

public class ArticleImageOrderCollection {
    private final HashMap<String, Article> articles;
    private final HashMap<String, List<ImageOrder>> articleImageOrders;
    private final HashMap<String, Article> filenameArticleRelations;

    public ArticleImageOrderCollection() {
        articles = new HashMap<>();
        articleImageOrders = new HashMap<>();
        filenameArticleRelations = new HashMap<>();
    }

    // Setting
    public void addArticle(Article article) {
        if (!articles.containsKey(article.getArticleNumber()))
            articles.put(article.getArticleNumber(), article);
    }

    public void addImageOrderToArticle(ImageOrder imageOrder, String articleNumber) {
        if (!articles.containsKey(articleNumber))
            throw new ArticleMissing(articleNumber);
        if (!articleImageOrders.containsKey(articleNumber))
            articleImageOrders.put(articleNumber, new ArrayList<>());

        List<ImageOrder> imageOrdersForArticle = articleImageOrders.get(articleNumber);
        setUniqueIdCounterForImageOrder(imageOrder, imageOrdersForArticle);
        imageOrdersForArticle.add(imageOrder);
    }

    public void addImageOrderToArticle(ImageOrder imageOrder, Article article) {
        addArticle(article);
        addImageOrderToArticle(imageOrder, article.getArticleNumber());
        for (String filename : imageOrder.getFilenames())
            filenameArticleRelations.put(filename, article);
        for (String filename : imageOrder.getFilenamesMirror())
            filenameArticleRelations.put(filename, article);
    }

    private void setUniqueIdCounterForImageOrder(ImageOrder imageOrder, List<ImageOrder> imageOrderList) {
        while (imageOrderList.contains(imageOrder))
            imageOrder.increaseIdCounter();
    }

    // Getting
    public List<String> getArticleNumbers() {
        return new ArrayList<>(articles.keySet());
    }

    public List<Article> getArticlesAsList() {
        return new ArrayList<>(articles.values());
    }

    public Iterator<ImageOrder> getImageOrdersForArticleAsIterator(Article article) {
        return getImageOrdersForArticleAsIterator(article.getArticleNumber());
    }

    public Iterator<ImageOrder> getImageOrdersForArticleAsIterator(String articleNumber) {
        return getImageOrdersForArticle(articleNumber).iterator();
    }

    public List<ImageOrder> getImageOrdersForArticle(String articleNumber) {
        if (!articleImageOrders.containsKey(articleNumber))
            articleImageOrders.put(articleNumber, new ArrayList<>());

        sortImageOrdersForArticle(articleNumber);

        return articleImageOrders.get(articleNumber);
    }

    public Article getArticle(String articleNumber) {
        return articles.get(articleNumber);
    }

    private void sortImageOrdersForArticle(String articleNumber) {
        articleImageOrders.get(articleNumber).sort((o1, o2) -> {
            try {
                int order1;
                int order2;
                order1 = o1.getDetailfotografie().getOrder();
                order2 = o2.getDetailfotografie().getOrder();
                return order1 - order2;
            } catch (InvalidDetailFotografie e) {
                return 99;
            }
        });
    }

    public HashSet<String> getStyleIds() {
        HashSet<String> styleIds = new HashSet<>();
        for (Article article : articles.values())
            styleIds.add(article.getStyleId());
        return styleIds;
    }

    public HashSet<Materials> getMaterialsForImageGroupHash(int imageGroupHash) {
        HashSet<Materials> materials = new HashSet<>();
        for (Article article : articles.values())
            if (article.getImageGroup().hashCode() == imageGroupHash)
                materials.add(article.getMaterials());
        return materials;
    }

    public HashSet<String> getFunctionsForImageGroupHash(int imageGroupHash) {
        HashSet<String> functions = new HashSet<>();
        functions.add("");
        for (Article article : articles.values())
            if (article.getImageGroup().hashCode() == imageGroupHash)
                functions.add(article.getArticleFunctionDescription());
        return functions;
    }

    public HashMap<String, Article> getFilenameArticleRelations() {
        return filenameArticleRelations;
    }

    public Collection<ImageGroupCharacteristics> getImageGroupCharacteristics() {
        HashMap<Integer, ImageGroupCharacteristics> imageGroupHashs = new HashMap<>();
        for (Article article : articles.values())
            imageGroupHashs.put(article.getImageGroup().hashCode(), article.getImageGroup());
        return imageGroupHashs.values();
    }
}
