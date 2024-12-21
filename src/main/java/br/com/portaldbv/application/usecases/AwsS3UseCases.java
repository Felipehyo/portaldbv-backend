package br.com.portaldbv.application.usecases;

import br.com.portaldbv.application.gateways.AwsS3ClientGateway;
import br.com.portaldbv.domain.enums.error.AwsErrorEnum;
import br.com.portaldbv.domain.exceptions.DomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Slf4j
public class AwsS3UseCases {

    private final AwsS3ClientGateway client;
    private final String awsRegion;

    public AwsS3UseCases(AwsS3ClientGateway client, String awsRegion) {
        this.client = client;
        this.awsRegion = awsRegion;
    }

    public String updateFile(MultipartFile multipartFile, String pathName, String imageUrl, String s3BucketName) {
        var file = convertMultiPartToFile(multipartFile);
        var fileName = extractFileName(imageUrl);
        return saveS3File(pathName, file, fileName, s3BucketName);
    }

    public String saveFile(MultipartFile multipartFile, String pathName, String s3BucketName) {
        var file = convertMultiPartToFile(multipartFile);
        return saveS3File(pathName, file, file.getName(), s3BucketName);
    }

    private String saveS3File(String pathName, File file, String fileName, String s3BucketName) {
        try {
            client.saveFile(file, s3BucketName, pathName + "/" + fileName + ".png");
            return getUrl(s3BucketName, pathName + "/" + fileName + ".png");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DomainException(AwsErrorEnum.S3_ERROR_SAVING);
        }
    }

    private String getUrl(String bucketName, String s3FileName) {
        return "https://" + bucketName + ".s3." + awsRegion + ".amazonaws.com/" + s3FileName;
    }

    private String extractFileName(String urlImage) {
        String[] parts = urlImage.split("[/\\\\]");
        return parts[parts.length - 1];
    }

    private File convertMultiPartToFile(MultipartFile file) {
        File convFile = new File(UUID.randomUUID().toString());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DomainException(AwsErrorEnum.S3_FILE_CONVERT_ERROR);
        }
        return convFile;
    }

    public void deleteFile(String linkFile, String s3BucketName) {
        try {
            client.deleteFile(linkFile.split("amazonaws.com/")[1], s3BucketName);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DomainException(AwsErrorEnum.S3_ERROR_SAVING);
        }
    }

}
