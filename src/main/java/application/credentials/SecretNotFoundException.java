package application.credentials;

public class SecretNotFoundException extends Exception {
    public SecretNotFoundException(String secretKey) {
        super(secretKey);
    }
}
