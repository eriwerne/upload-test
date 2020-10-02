package injection;

import application.asyncinvocation.AsyncProcessCall;
import application.credentials.CredentialStore;
import application.output.Persister;
import application.reading.articlereader.ArticleReaderSource;
import application.reading.imageorderreader.ImageOrderReaderSource;
import application.reading.orderreader.OrderReaderSource;
import dagger.Module;
import dagger.Provides;
import infrastructure.aws.lambda.AsyncLambdaProcessCall;
import infrastructure.aws.secretsmanager.AwsSecretsManager;
import infrastructure.filesystem.FilePersister;
import infrastructure.m2.M2;
import infrastructure.m2.M2Api;
import infrastructure.m2.M2ArticleReaderSource;
import infrastructure.mmp.Mmp;
import infrastructure.mmp.MmpImageOrderReaderSource;
import infrastructure.mmp.MmpOrderReaderSource;


@SuppressWarnings("unused")
@Module
public class DaggerInjectionModule {
    static OrderReaderSource orderReaderSource;
    static ImageOrderReaderSource imageOrderReaderSource;
    static ArticleReaderSource articleReaderSource;
    static AsyncProcessCall asyncProcessCall;
    static Persister persister;
    private Mmp mmpApi;
    private M2Api m2Api;
    private AwsSecretsManager awsSecretsManager;

    //Persistence
    @Provides
    public Persister providesPersister() {
        if (persister == null)
            persister = new FilePersister();
        return persister;
    }

    //APIs
    @Provides
    public ArticleReaderSource providesArticleReaderSource() {
        if (articleReaderSource == null)
            articleReaderSource = new M2ArticleReaderSource(getM2Api());
        return articleReaderSource;
    }

    private M2Api getM2Api() {
        if (m2Api == null)
            m2Api = new M2();
        return m2Api;
    }

    @Provides
    public OrderReaderSource providesOrderReaderSource() {
        if (orderReaderSource == null)
            orderReaderSource = new MmpOrderReaderSource(getMmpApi());
        return orderReaderSource;
    }

    @Provides
    public ImageOrderReaderSource providesImageReaderSource() {
        if (imageOrderReaderSource == null)
            imageOrderReaderSource = new MmpImageOrderReaderSource(getMmpApi());
        return imageOrderReaderSource;
    }

    public Mmp getMmpApi() {
        if (mmpApi == null)
            mmpApi = new Mmp();
        return mmpApi;
    }

    //Lambda
    @Provides
    public AsyncProcessCall providesAsyncProcessStarter() {
        if (asyncProcessCall == null)
            asyncProcessCall = new AsyncLambdaProcessCall();
        return asyncProcessCall;
    }

    //Credentials
    @Provides
    public CredentialStore providesCredentialStore() {
        if (awsSecretsManager == null)
            awsSecretsManager = new AwsSecretsManager();
        return awsSecretsManager;
    }
}
