package injection;

import application.reading.exception.ResourceFailure;
import infrastructure.m2.M2Api;
import infrastructure.m2.M2ArticleReaderSource;
import utils.ResourceFileReader;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class M2Mock {
    private final M2Api m2Api;

    public M2Mock() {
        m2Api = mock(M2Api.class);
        DaggerInjectionModule.articleReaderSource = new M2ArticleReaderSource(m2Api);
    }

    public void mockM2ApiResponse(String filePath, String articleNumber) {
        try {
            String resource = ResourceFileReader.readResource(filePath);
            when(m2Api.getArticleString(articleNumber)).thenReturn(resource);
        } catch (IOException | ResourceFailure e) {
            throw new RuntimeException(e);
        }
    }

    public M2Api getM2Mock() {
        return m2Api;
    }
}
