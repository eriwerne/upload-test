package infrastructure.aws.s3;

import application.output.Persister;
import application.output.PersisterFailure;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class S3Persister implements Persister {
    private final AmazonS3 amazonS3;
    private final String bucketName;

    public S3Persister(String bucketName) {
        this.amazonS3 = AmazonS3ClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).build();
        this.bucketName = bucketName;
    }

    @Override
    public void persistString(String folderName, String fileName, String output) throws PersisterFailure {
        try {
            amazonS3.putObject(bucketName, folderName + fileName, output);
        } catch (Exception e) {
            throw new PersisterFailure(e);
        }
    }

    @Override
    public boolean pathExists(String folderPath, String fileName) throws PersisterFailure {
        try {
            return amazonS3.doesObjectExist(bucketName, folderPath + fileName);
        } catch (Exception e) {
            throw new PersisterFailure(e);
        }
    }

    @Override
    public String getPersistedString(String folderPath, String fileName) throws PersisterFailure {
        try {
            S3Object s3Object = amazonS3.getObject(bucketName, folderPath + fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(s3Object.getObjectContent()));
            StringBuilder output = new StringBuilder();
            while (true) {
                String line = reader.readLine();
                if (line == null)
                    break;
                output.append(line);
            }
            return output.toString();

        } catch (IOException e) {
            throw new PersisterFailure(e);
        }
    }

    @Override
    public void remove(String folderPath, String fileName) throws PersisterFailure {
        try {
            amazonS3.deleteObject(bucketName, folderPath + fileName);
        } catch (Exception e) {
            throw new PersisterFailure(e);
        }
    }

    @Override
    public void removeFolder(String folder) {
        for (S3ObjectSummary file : amazonS3.listObjects(bucketName, folder).getObjectSummaries()) {
            amazonS3.deleteObject(bucketName, file.getKey());
        }
    }
}
