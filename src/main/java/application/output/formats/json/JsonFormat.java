package application.output.formats.json;

import application.output.formats.OutputFormat;
import core.order.Order;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class JsonFormat implements OutputFormat {

    private final ObjectMapper objectMapper;

    public JsonFormat() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public String getOutputFromOrder(Order order) throws IOException {
        JsonNodeOrder jsonNodeOrder = new JsonNodeOrder(order);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNodeOrder);
    }

}
