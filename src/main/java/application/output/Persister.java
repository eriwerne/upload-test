package application.output;

public interface Persister {
    void persistString(String folderName, String fileName, String output) throws PersisterFailure;

    boolean pathExists(String folderPath, String fileName) throws PersisterFailure;

    String getPersistedString(String folderPath, String fileName) throws PersisterFailure;

    void remove(String folderPath, String fileName) throws PersisterFailure;

    void removeFolder(String folder) throws PersisterFailure;
}
