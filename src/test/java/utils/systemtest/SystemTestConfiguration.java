package utils.systemtest;

import application.output.formats.OutputFormatType;

import java.util.HashMap;

public class SystemTestConfiguration {
    private final HashMap<OutputFormatType, String> expectationFiles = new HashMap<>();
    private String systemTestDirectory;
    private String orderNumber;
    private boolean expectsException;

    public void addExpectationFileName(String expectationFileName, OutputFormatType formatType) {
        this.expectationFiles.put(formatType, expectationFileName);
    }

    public String getSystemTestDirectory() {
        return systemTestDirectory;
    }

    public void setSystemTestDirectory(String systemTestDirectory) {
        this.systemTestDirectory = systemTestDirectory;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public boolean expectsException() {
        return expectsException;
    }

    public void setExpectsException() {
        this.expectsException = true;
    }

    public HashMap<OutputFormatType, String> getExpectationFiles() {
        return expectationFiles;
    }
}
