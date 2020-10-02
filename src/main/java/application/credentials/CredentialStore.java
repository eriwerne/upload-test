package application.credentials;

public interface CredentialStore {
    String getSecretValueForKey(String secretKey) throws SecretNotFoundException;
}
