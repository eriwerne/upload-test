package injection;

import application.reading.imageorderreader.ImageOrderReaderSource;

import static org.mockito.Mockito.mock;

public class ImageOrderReaderSourceMock {
    private final ImageOrderReaderSource imageOrderReaderSource = mock(ImageOrderReaderSource.class);

    public ImageOrderReaderSourceMock() {
        DaggerInjectionModule.imageOrderReaderSource = imageOrderReaderSource;
    }

    public ImageOrderReaderSource getImageOrderReaderSourceMock() {
        return imageOrderReaderSource;
    }
}
