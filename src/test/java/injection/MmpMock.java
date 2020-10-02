package injection;

import application.reading.exception.ResourceFailure;
import infrastructure.mmp.MmpApi;
import infrastructure.mmp.MmpImageOrderReaderSource;
import infrastructure.mmp.MmpOrderReaderSource;
import utils.ResourceFileReader;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MmpMock {
    private final MmpApi mmpApi = mock(MmpApi.class);

    public MmpMock() {
        DaggerInjectionModule.orderReaderSource = new MmpOrderReaderSource(mmpApi);
        DaggerInjectionModule.imageOrderReaderSource = new MmpImageOrderReaderSource(mmpApi);
    }

    public void mockMmpApiResponse(String filePath, String orderNumber) {
        try {
            String resource = ResourceFileReader.readResource(filePath);
            when(mmpApi.getImageOrdersForOrderNumber(orderNumber)).thenReturn(resource);
        } catch (IOException | ResourceFailure e) {
            throw new RuntimeException(e);
        }
    }

    public MmpApi getMmpMock() {
        return mmpApi;
    }
}
