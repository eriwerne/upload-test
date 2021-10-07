package config;

public class Configuration {
    public static final String mmpProdUrl = System.getenv("MMP_PROD_URL");
    public static final String m2ProdUrl = System.getenv("M2_PROD_URL");
    public static final String m2SecretName = System.getenv("M2_SECRET_NAME");
    public static final String mmpSecretName = System.getenv("MMP_SECRET_NAME");
    public static final String awsRegion = System.getenv("AWS_REGION");
}
