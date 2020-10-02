package infrastructure.http;

import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;

public class HttpResponseValidator {
    public void validateResponseStatus(StatusLine statusLine) throws HttpResponseException {
        if (statusLine.getStatusCode() != 200) {
            System.out.println("Status Code " + statusLine.getStatusCode());
            throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
        }
    }
}
