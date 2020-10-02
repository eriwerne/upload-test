package utils;

import java.io.PrintStream;
import java.io.PrintWriter;

public class ResourceNotFound extends Throwable {
    private final String directory;

    ResourceNotFound(String directory) {

        this.directory = directory;
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        System.out.println("Resource not found " + directory);
        super.printStackTrace(s);
    }

    @Override
    public void printStackTrace(PrintStream s) {
        System.out.println("Resource not found " + directory);
        super.printStackTrace(s);
    }

    @Override
    public void printStackTrace() {
        System.out.println("Resource not found " + directory);
        super.printStackTrace();
    }
}
