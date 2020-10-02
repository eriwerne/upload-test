package infrastructure.http;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * Fix for Exception in thread "main" javax.net.ssl.SSLHandshakeException:
 * sun.security.validator.ValidatorException: PKIX path building failed:
 * sun.security.provider.certpath.SunCertPathBuilderException: unable to find
 * valid certification path to requested target
 */
public class ConnectToHttpsUrl {
    public static HttpClient getTrustedHttpClient() {
        /* Start of Fix */
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }

        }};

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());

            //noinspection deprecation
            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sc,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            return HttpClientBuilder.create().setSSLSocketFactory(socketFactory).build();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }
}
