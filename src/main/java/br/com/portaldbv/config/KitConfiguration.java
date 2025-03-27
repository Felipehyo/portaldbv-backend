package br.com.portaldbv.config;

import br.com.portaldbv.application.gateways.KitRepositoryGateway;
import br.com.portaldbv.application.usecases.KitUseCases;
import br.com.portaldbv.application.usecases.UserUseCases;
import br.com.portaldbv.infra.gateway.KitRepositoryGatewayImpl;
import br.com.portaldbv.infra.mapper.KitMapper;
import br.com.portaldbv.infra.persistence.repository.KitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KitConfiguration {

    @Bean
    KitUseCases kitUseCases(KitRepositoryGateway repositoryGateway, @Autowired UserUseCases userUseCases) {
        return new KitUseCases(repositoryGateway, userUseCases);
    }

    @Bean
    KitRepositoryGatewayImpl kitRepositoryGateway(KitRepository repository, KitMapper mapper) {
        return new KitRepositoryGatewayImpl(repository, mapper);
    }

}
