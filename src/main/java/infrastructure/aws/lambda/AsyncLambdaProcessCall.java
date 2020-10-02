package infrastructure.aws.lambda;

import application.asyncinvocation.AsyncProcessCall;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvocationType;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;

public class AsyncLambdaProcessCall implements AsyncProcessCall {
    final LambdaClient lambdaClient;

    public AsyncLambdaProcessCall() {
        lambdaClient = LambdaClient.builder()
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .region(Region.EU_CENTRAL_1)
                .httpClientBuilder(UrlConnectionHttpClient.builder())
                .build();
    }

    @Override
    public void executeOrder(String orderNumber, String outputFilename, String exceptionDocumentName, String folderPath, String startedFlagFilename) {
        SdkBytes payload = SdkBytes.fromUtf8String("{\"orderNumber\":\"" + orderNumber + "\",\"outputFilename\":\"" + outputFilename + "\",\"exceptionDocumentName\":\"" + exceptionDocumentName + "\",\"folderPath\":\"" + folderPath + "\",\"startedFlagFilename\":\"" + startedFlagFilename + "\"}");
        InvokeRequest invokeRequest = InvokeRequest.builder()
                .functionName("order-execution")
                .invocationType(InvocationType.EVENT)
                .payload(payload)
                .build();
        InvokeResponse invokeResponse = lambdaClient.invoke(invokeRequest);
        System.out.println("Response: " + invokeResponse.toString());
        System.out.println("Response Payload: " + invokeResponse.payload().asUtf8String());
    }
}
