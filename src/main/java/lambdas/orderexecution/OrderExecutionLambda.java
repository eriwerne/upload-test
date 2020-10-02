package lambdas.orderexecution;

import application.ProcessMediator;
import application.asyncinvocation.OrderExecutionHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@SuppressWarnings("unused")
public class OrderExecutionLambda implements RequestStreamHandler {

    private final OrderExecutionHandler orderExecutionHandler;

    public OrderExecutionLambda() {
        orderExecutionHandler = new OrderExecutionHandler(new ProcessMediator());
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        String json = IOUtils.toString(inputStream);
        OrderExecutionPayload payload = new Gson().fromJson(json, OrderExecutionPayload.class);
        orderExecutionHandler.executeOrder(
                payload.getOrderNumber(),
                payload.getOutputFilename(),
                payload.getExceptionDocumentName(),
                payload.getFolderPath(),
                payload.getStartedFlagFilename()
        );
    }
}
