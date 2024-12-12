package br.com.portaldbv.config;

import br.com.portaldbv.application.gateways.SpecialityRepositoryGateway;
import br.com.portaldbv.application.usecases.AwsS3UseCases;
import br.com.portaldbv.application.usecases.SpecialityUseCases;
import br.com.portaldbv.infra.gateway.SpecialityRepositoryGatewayImpl;
import br.com.portaldbv.infra.mapper.SpecialityMapper;
import br.com.portaldbv.infra.persistence.repository.SpecialityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpecialityConfiguration {

    @Autowired
    private AwsS3UseCases awsS3UseCases;

    @Value("${backend-configs.aws.s3.bucket}")
    private String s3BucketName;

    @Bean
    SpecialityUseCases specialityUseCases(SpecialityRepositoryGateway repositoryGateway) {
        return new SpecialityUseCases(repositoryGateway, awsS3UseCases, s3BucketName);
    }

    @Bean
    SpecialityRepositoryGatewayImpl repositoryGateway(SpecialityRepository repository, SpecialityMapper mapper) {
        return new SpecialityRepositoryGatewayImpl(repository, mapper);
    }

}
