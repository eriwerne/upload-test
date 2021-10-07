package infrastructure.aws.secretsmanager;

import application.credentials.CredentialStore;
import application.credentials.SecretNotFoundException;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import config.Configuration;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class AwsSecretsManager implements CredentialStore {
    private final Map<String, Object> secretsMap;

    public AwsSecretsManager() {
        secretsMap = new HashMap<>();
        initSecretsMap(Configuration.m2SecretName).forEach(secretsMap::put);
        initSecretsMap(Configuration.mmpSecretName).forEach(secretsMap::put);
    }

    @Override
    public String getSecretValueForKey(String secretKey) throws SecretNotFoundException {
        String secretValue = (String) secretsMap.get(secretKey);
        if (secretValue == null)
            throw new SecretNotFoundException(secretKey);
        return secretValue;
    }

    private Map<String, Object> initSecretsMap(String secretName) {
        AWSSecretsManager awsSecretsManager = AWSSecretsManagerClientBuilder.standard()
                .withRegion(Configuration.awsRegion)
                .build();
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                .withSecretId(secretName);

        GetSecretValueResult getSecretValueResult = awsSecretsManager.getSecretValue(getSecretValueRequest);

        String secret = getSecretValueResult.getSecretString();

        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        return new Gson().fromJson(secret, type);
    }
}
