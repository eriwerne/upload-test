package application.reading.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

public abstract class ResourceFailure extends Exception {
    private final Throwable rootException;
    private final String resourceName;

    protected ResourceFailure(Throwable rootException, String resourceName) {
        this.rootException = rootException;
        this.resourceName = "\"" + resourceName + "\"";
    }

    @Override
    public void printStackTrace() {
        System.out.println("Resource " + resourceName + " failed due to:");
        rootException.printStackTrace();
        System.out.println("Resource " + resourceName + " failed at:");
        rootException.printStackTrace();
        super.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream s) {
        s.println("Resource " + resourceName + " failed due to:");
        rootException.printStackTrace(s);
        s.println("Resource " + resourceName + " failed at:");
        rootException.printStackTrace(s);
        super.printStackTrace(s);
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        s.println("Resource " + resourceName + " failed due to:");
        rootException.printStackTrace(s);
        s.println("Resource " + resourceName + " failed at:");
        rootException.printStackTrace(s);
        super.printStackTrace(s);
    }
}
