package lambdas.asyncinvoker;

import application.asyncinvocation.AsyncServiceInvokerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@SuppressWarnings("unused")
public class AsyncServiceInvokerLambda implements RequestStreamHandler {
    final AsyncServiceInvokerHandler asyncServiceInvokerHandler;

    public AsyncServiceInvokerLambda() {
        asyncServiceInvokerHandler = new AsyncServiceInvokerHandler();
    }

    @Override
    public final void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        String json = IOUtils.toString(inputStream);
        AsyncServiceInvokerPayload payload = new Gson().fromJson(json, AsyncServiceInvokerPayload.class);
        String output = asyncServiceInvokerHandler.requestOrder(payload.getOrderNumber(), payload.getFormatType());
        AsyncServiceInvokerResponse response = new AsyncServiceInvokerResponse(output);
        outputStream.write(new Gson().toJson(response).getBytes());
    }
}
