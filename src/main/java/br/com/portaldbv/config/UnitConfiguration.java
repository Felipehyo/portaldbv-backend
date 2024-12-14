package br.com.portaldbv.config;

import br.com.portaldbv.application.gateways.UnitRepositoryGateway;
import br.com.portaldbv.application.usecases.AwsS3UseCases;
import br.com.portaldbv.application.usecases.UnitUseCases;
import br.com.portaldbv.infra.gateway.UnitRepositoryGatewayImpl;
import br.com.portaldbv.infra.mapper.UnitMapper;
import br.com.portaldbv.infra.persistence.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UnitConfiguration {

    @Autowired
    private AwsS3UseCases awsS3UseCases;

    @Value("${backend-configs.aws.s3.bucket}")
    private String s3BucketName;

    @Bean
    UnitUseCases unitUseCases(UnitRepositoryGateway repositoryGateway) {
        return new UnitUseCases(repositoryGateway, awsS3UseCases, s3BucketName);
    }

    @Bean
    UnitRepositoryGatewayImpl unitRepositoryGateway(UnitRepository repository, UnitMapper mapper) {
        return new UnitRepositoryGatewayImpl(repository, mapper);
    }

}
