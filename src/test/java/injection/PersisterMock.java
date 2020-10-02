package injection;

import application.output.Persister;
import injection.DaggerInjectionModule;

import static org.mockito.Mockito.mock;

public class PersisterMock {
    private final Persister persisterMock = mock(Persister.class);

    public PersisterMock() {
        DaggerInjectionModule.persister = persisterMock;
    }

    public Persister getPersisterMock() {
        return persisterMock;
    }
}
