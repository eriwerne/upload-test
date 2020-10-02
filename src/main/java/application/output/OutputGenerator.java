package application.output;

import application.output.formats.OutputFormat;
import application.output.formats.OutputFormatType;
import core.order.Order;
import injection.DaggerOutputGeneratorInjection;

import javax.inject.Inject;
import java.util.HashMap;

public class OutputGenerator {
    private final HashMap<OutputFormatType, String> outputStrings = new HashMap<>();
    @Inject
    Persister persister;

    public OutputGenerator() {
        DaggerOutputGeneratorInjection.builder().build().inject(this);
    }

    public void run(Order order, String filename, String foldername) throws OutputFailed {
        try {
            for (OutputFormatType formatType : OutputFormatType.values()) {
                OutputFormat formatting = formatType.getOutputFormatting();
                String output = formatting.getOutputFromOrder(order);
                persister.persistString(foldername, filename + formatType.getFileExtension(), output);
                outputStrings.put(formatType, output);
            }
        } catch (Exception e) {
            throw new OutputFailed(e);
        }
    }

    public HashMap<OutputFormatType, String> getOutputStrings() {
        return outputStrings;
    }

}
