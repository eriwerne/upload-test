package infrastructure.m2;

import application.credentials.CredentialStore;
import application.reading.exception.ResourceFailure;
import config.Configuration;
import infrastructure.http.ConnectToHttpsUrl;
import infrastructure.http.HttpResponseValidator;
import injection.DaggerM2Injection;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;

import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;

public class M2 implements M2Api {
    private final HashMap<String, String> articleCache;
    private final HttpResponseValidator httpResponseValidator;
    private String basicAuthToken;
    @Inject
    CredentialStore credentialStore;

    public M2() {
        DaggerM2Injection.builder().build().inject(this);
        articleCache = new HashMap<>();
        httpResponseValidator = new HttpResponseValidator();
    }

    @Override
    public String getArticleString(String articleNumber) throws ResourceFailure {
        if (!articleCache.containsKey(articleNumber))
            readArticleFromM2(articleNumber);
        return articleCache.get(articleNumber);
    }

    private void readArticleFromM2(String articleNumber) throws ResourceFailure {
        try {
            System.out.println("Reads article from M2: " + articleNumber);
            if (basicAuthToken == null) {
                String m2ProdUser = credentialStore.getSecretValueForKey("m2ProdUser");
                String m2ProdPassword = credentialStore.getSecretValueForKey("m2ProdPassword");
                basicAuthToken = "Basic " + Base64.getEncoder().encodeToString((m2ProdUser + ":" + m2ProdPassword).getBytes());
            }
            HttpUriRequest request = RequestBuilder.get()
                    .setUri(Configuration.m2ProdUrl + "/search/article_number/" + articleNumber)
                    .setHeader(HttpHeaders.AUTHORIZATION, basicAuthToken)
                    .setHeader(HttpHeaders.ACCEPT, "*/*")
                    .setHeader(HttpHeaders.USER_AGENT, "PostmanRuntime/7.24.0")
                    .build();

            HttpClient client = ConnectToHttpsUrl.getTrustedHttpClient();
            HttpResponse response = client.execute(request);
            httpResponseValidator.validateResponseStatus(response.getStatusLine());

            String json = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8.name());
            articleCache.put(articleNumber, json);
        } catch (Throwable e) {
            throw new M2ConnectionFailure(e);
        }
    }
}
