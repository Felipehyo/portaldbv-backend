package br.com.portaldbv.config;

import br.com.portaldbv.application.gateways.ClubRepositoryGateway;
import br.com.portaldbv.application.usecases.ClubUseCases;
import br.com.portaldbv.infra.gateway.ClubRepositoryGatewayImpl;
import br.com.portaldbv.infra.mapper.ClubMapper;
import br.com.portaldbv.infra.persistence.repository.ClubRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClubConfiguration {

    @Bean
    ClubUseCases clubUseCases(ClubRepositoryGateway repositoryGateway) {
        return new ClubUseCases(repositoryGateway);
    }

    @Bean
    ClubRepositoryGatewayImpl clubRepositoryGateway(ClubRepository repository, ClubMapper mapper) {
        return new ClubRepositoryGatewayImpl(repository, mapper);
    }

}
