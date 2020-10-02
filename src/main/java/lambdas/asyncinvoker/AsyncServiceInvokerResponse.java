package lambdas.asyncinvoker;

@SuppressWarnings("unused")
public class AsyncServiceInvokerResponse {
    private final String output;

    public AsyncServiceInvokerResponse(String output) {
        this.output = output;
    }

    public String getOutput() {
        return output;
    }
}
