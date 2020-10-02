package injection;

import application.asyncinvocation.AsyncProcessCall;

import static org.mockito.Mockito.mock;

public class AsyncProcessStarterMock {
    private final AsyncProcessCall asyncProcessCall = mock(AsyncProcessCall.class);

    public AsyncProcessStarterMock() {
        DaggerInjectionModule.asyncProcessCall = asyncProcessCall;
    }

    public AsyncProcessCall getAsyncProcessStarterMock() {
        return asyncProcessCall;
    }
}
