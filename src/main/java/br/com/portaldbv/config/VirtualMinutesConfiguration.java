package br.com.portaldbv.config;

import br.com.portaldbv.application.gateways.VirtualMinutesRepositoryGateway;
import br.com.portaldbv.application.usecases.AwsS3UseCases;
import br.com.portaldbv.application.usecases.UnitUseCases;
import br.com.portaldbv.application.usecases.UserUseCases;
import br.com.portaldbv.application.usecases.VirtualMinutesUseCases;
import br.com.portaldbv.infra.gateway.VirtualMinutesRepositoryGatewayImpl;
import br.com.portaldbv.infra.mapper.VirtualMinutesMapper;
import br.com.portaldbv.infra.persistence.repository.VirtualMinutesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VirtualMinutesConfiguration {

    @Autowired
    private UnitUseCases unitUseCases;

    @Autowired
    private UserUseCases userUseCases;

    @Autowired
    private AwsS3UseCases awsS3UseCases;

    @Value("${backend-configs.aws.s3.bucket}")
    private String s3BucketName;

    @Bean
    VirtualMinutesUseCases virtualMinutesUseCases(VirtualMinutesRepositoryGateway repositoryGateway) {
        return new VirtualMinutesUseCases(repositoryGateway, unitUseCases, userUseCases, awsS3UseCases, s3BucketName);
    }

    @Bean
    VirtualMinutesRepositoryGatewayImpl virtualMinutesRepositoryGateway(VirtualMinutesRepository repository, VirtualMinutesMapper mapper) {
        return new VirtualMinutesRepositoryGatewayImpl(repository, mapper);
    }

}

