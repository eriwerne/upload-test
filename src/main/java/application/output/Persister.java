package application.output;

import java.io.Serializable;

public interface Persister {
    void persistString(String folderName, String fileName, String output) throws PersisterFailure;

    boolean pathExists(String folderPath, String fileName) throws PersisterFailure;

    String getPersistedString(String folderPath, String fileName) throws PersisterFailure;

    void remove(String folderPath, String fileName) throws PersisterFailure;

    Object getObject(String folderPath, String fileName) throws PersisterFailure;

    void persistObject(String folderPath, String fileName, Serializable object) throws PersisterFailure;

    void removeFolder(String folder) throws PersisterFailure;
}
