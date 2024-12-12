package br.com.portaldbv.config;

import br.com.portaldbv.application.gateways.AwsS3ClientGateway;
import br.com.portaldbv.application.usecases.AwsS3UseCases;
import br.com.portaldbv.infra.client.AwsS3ClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsConfiguration {

    @Value("${backend-configs.aws.credentials.key.id}")
    private String accessKeyId;

    @Value("${backend-configs.aws.credentials.secret.key}")
    private String secretAccessKey;

    @Value("${backend-configs.aws.region}")
    private String region;

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .region(Region.of(region))
                .build();
    }

    @Bean
    AwsS3UseCases awsS3UseCases(AwsS3ClientGateway repositoryGateway) {
        return new AwsS3UseCases(repositoryGateway, region);
    }

    @Bean
    AwsS3ClientImpl awsS3Client() {
        return new AwsS3ClientImpl(s3Client());
    }

}
