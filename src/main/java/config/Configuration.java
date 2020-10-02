package config;

public class Configuration {
    public static final String mmpProdUrl = System.getenv("MMP_PROD_URL");
    public static final String m2ProdUrl = System.getenv("M2_PROD_URL");
    public static final String secretName = System.getenv("SECRET_NAME");
    public static final String awsRegion = System.getenv("AWS_REGION");
}
