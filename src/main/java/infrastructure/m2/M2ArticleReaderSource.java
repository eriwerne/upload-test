package infrastructure.m2;

import application.reading.articlereader.ArticleReaderSource;
import application.reading.exception.ArticleNotFound;
import application.reading.exception.ResourceFailure;
import core.article.Article;
import core.article.exceptions.InvalidArticleData;
import infrastructure.m2.mapping.M2ArticleMapper;

import java.util.ArrayList;
import java.util.List;

public class M2ArticleReaderSource implements ArticleReaderSource {
    private final M2ArticleMapper mapper;
    private final M2Api m2Api;

    public M2ArticleReaderSource(M2Api m2Api) {
        this.m2Api = m2Api;
        this.mapper = new M2ArticleMapper();
    }

    @Override
    public List<Article> readArticles(List<String> articleNumbers) throws ArticleNotFound, InvalidArticleData, ResourceFailure {
        int readIndex = 0;
        List<Article> articleList = new ArrayList<>();
        for (String articleNumber : articleNumbers) {
            printProgressBar(readIndex, articleNumbers.size());
            String json = m2Api.getArticleString(articleNumber);
            if (json.equals("[]\n"))
                throw new ArticleNotFound(articleNumber);
            Article article = createArticleFromJson(json);
            articleList.add(article);
            readIndex++;
        }
        printProgressBar(articleNumbers.size(), articleNumbers.size());
        return articleList;
    }

    private void printProgressBar(int index, int end) {
        if (end > 10) {
            StringBuilder progress = new StringBuilder();
            int percentage = 100 * index / end;
            for (int i = 0; i <= 100; i++) {
                if (i <= percentage)
                    progress.append(">");
                else
                    progress.append(".");
            }
            System.out.print("M2 article reading: " + percentage + "% " + progress + " ");
        }
    }

    private Article createArticleFromJson(String json) throws InvalidArticleData {
        try {
            return mapper.mapFromJson(json);
        } catch (InvalidArticleData e) {
            System.out.println("Error while parsing article: \n" + json + "\n");
            throw e;
        }
    }

}
