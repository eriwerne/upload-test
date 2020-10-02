package lambdas.orderreset;

import application.output.PersisterFailure;
import application.orderreset.OrderResetHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@SuppressWarnings("unused")
public class OrderResetLambda implements RequestStreamHandler {
    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        try {
            String json = IOUtils.toString(inputStream);
            OrderResetPayload payload = new Gson().fromJson(json, OrderResetPayload.class);
            new OrderResetHandler().resetOrder(payload.getOrderNumber());
            outputStream.write(("Order " + payload.getOrderNumber() + " reseted").getBytes());
        } catch (PersisterFailure e) {
            throw new RuntimeException(e);
        }
    }
}
