package br.com.portaldbv.application.gateways;

import software.amazon.awssdk.regions.Region;

import java.io.File;

public interface AwsS3ClientGateway {

    void saveFile(File file, String bucket, String fileName);

    String generatePreSignedUrl(Region region, String bucketName, String objectKey, Integer signatureDuration);

}
