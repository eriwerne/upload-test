package infrastructure.mmp;

import application.credentials.CredentialStore;
import application.credentials.SecretNotFoundException;
import application.reading.exception.ResourceFailure;
import config.Configuration;
import infrastructure.http.ConnectToHttpsUrl;
import infrastructure.http.HttpResponseValidator;
import injection.DaggerMmpInjection;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;


public class Mmp implements MmpApi {
    private final HashMap<String, String> orderCache;
    private final HttpResponseValidator httpResponseValidator;
    private final HttpClient client;
    private String sessionToken;
    @Inject
    CredentialStore credentialStore;

    public Mmp() {
        DaggerMmpInjection.builder().build().inject(this);
        orderCache = new HashMap<>();
        httpResponseValidator = new HttpResponseValidator();
        client = ConnectToHttpsUrl.getTrustedHttpClient();
    }

    @Override
    public String getImageOrdersForOrderNumber(String orderNumber) throws ResourceFailure {
        if (!orderCache.containsKey(orderNumber))
            readOrderFromMMP(orderNumber);
        return orderCache.get(orderNumber);
    }

    private void readOrderFromMMP(String orderNumber) throws ResourceFailure {
        try {
            if (sessionToken == null)
                sessionToken = requestSessionToken();
            System.out.println("Reads from MMP: " + orderNumber);
            HttpUriRequest request = RequestBuilder.get()
                    .setUri(Configuration.mmpProdUrl + "/Auftraege/getContainerCodes/" + orderNumber)
                    .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + sessionToken)
                    .setHeader(HttpHeaders.ACCEPT, "*/*")
                    .setHeader(HttpHeaders.USER_AGENT, "PostmanRuntime/7.24.0")
                    .build();

            HttpResponse response = client.execute(request);

            httpResponseValidator.validateResponseStatus(response.getStatusLine());

            String json = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8.name());
            orderCache.put(orderNumber, json);
        } catch (IOException e) {
            throw new MmpConnectionFailure(e);
        }
    }

    private String requestSessionToken() throws ResourceFailure {
        try {
            HttpPost post = new HttpPost(Configuration.mmpProdUrl + "/Session/statelessLogin");
            String body =
                    "{\"tenant\": \"OV\"," +
                            "\"username\": \"" + credentialStore.getSecretValueForKey("mmpProdUser") + "\"," +
                            "\"password\": \"" + credentialStore.getSecretValueForKey("mmpProdPassword") + "\"}";
            post.setEntity(new StringEntity(body));

            HttpResponse response = client.execute(post);
            httpResponseValidator.validateResponseStatus(response.getStatusLine());
            String json = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8.name());
            return new JSONObject(json).getString("jwt");
        } catch (IOException | SecretNotFoundException e) {
            throw new MmpConnectionFailure(e);
        }
    }
}
