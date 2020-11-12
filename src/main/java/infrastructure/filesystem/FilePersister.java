package infrastructure.filesystem;

import application.output.Persister;
import application.output.PersisterFailure;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FilePersister implements Persister {

    @Override
    public void persistString(String folderPath, String fileName, String output) throws PersisterFailure {
        try {
            File file = getFile(folderPath, fileName);
            FileUtils.writeStringToFile(file, output, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new PersisterFailure(e);
        }
    }

    @Override
    public boolean pathExists(String folderPath, String fileName) {
        return getFile(folderPath, fileName).exists();
    }

    @Override
    public String getPersistedString(String folderPath, String fileName) throws PersisterFailure {
        try {
            File file = getFile(folderPath, fileName);
            return FileUtils.readFileToString(file);
        } catch (IOException e) {
            throw new PersisterFailure(e);
        }
    }

    @Override
    public void remove(String folderPath, String fileName) throws PersisterFailure {
        try {
            File file = getFile(folderPath, fileName);
            FileUtils.forceDelete(file);
        } catch (IOException e) {
            throw new PersisterFailure(e);
        }
    }

    @Override
    public void removeFolder(String folder) throws PersisterFailure {
        try {
            FileUtils.deleteDirectory(new File(folder));
        } catch (IOException e) {
            throw new PersisterFailure(e);
        }
    }

    private File getFile(String folderPath, String fileName) {
        String path;
        if (fileName.equals(""))
            path = folderPath;
        else
            path = folderPath + "/" + fileName;
        return new File(path);
    }
}
