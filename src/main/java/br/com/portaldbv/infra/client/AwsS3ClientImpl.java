package br.com.portaldbv.infra.client;

import br.com.portaldbv.application.gateways.AwsS3ClientGateway;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.File;
import java.nio.file.Paths;
import java.time.Duration;

@Slf4j
public class AwsS3ClientImpl implements AwsS3ClientGateway {

    private final S3Client s3Client;

    public AwsS3ClientImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public void saveFile(File file, String bucket, String fileName) {
        try {
            var putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .build();

            s3Client.putObject(putObjectRequest, Paths.get(file.getPath()));

        } catch (S3Exception e) {
            throw new RuntimeException("Erro ao atualizar arquivo: " + e.awsErrorDetails().errorMessage());
        } finally {
            var isDeleted = file.delete();
            if (!isDeleted) {
                log.error("Ocorreu um erro ao deletar o arquivo da memória temporária");
            }
        }
    }

    @Override
    public void deleteFile(String filePathAndName, String bucket) {
        try {
            var deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(filePathAndName)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
        } catch (S3Exception e) {
            throw new RuntimeException("Erro ao deletar arquivo: " + e.awsErrorDetails().errorMessage());
        }
    }

    public String generatePreSignedUrl(Region region, String bucketName, String objectKey, Integer signatureDuration) {
        var preSigner = S3Presigner.builder()
                .region(region)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();

        var getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        var preSignRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(getObjectRequest)
                .signatureDuration(Duration.ofMinutes(signatureDuration))
                .build();

        return preSigner.presignGetObject(preSignRequest).url().toString();
    }

}
