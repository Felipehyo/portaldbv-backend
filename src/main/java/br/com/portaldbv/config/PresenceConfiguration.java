package br.com.portaldbv.config;

import br.com.portaldbv.application.gateways.PresenceRepositoryGateway;
import br.com.portaldbv.application.usecases.KitUseCases;
import br.com.portaldbv.application.usecases.PresenceUseCases;
import br.com.portaldbv.application.usecases.UserUseCases;
import br.com.portaldbv.infra.gateway.PresenceRepositoryGatewayImpl;
import br.com.portaldbv.infra.mapper.PresenceMapper;
import br.com.portaldbv.infra.persistence.repository.PresenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PresenceConfiguration {

    @Bean
    PresenceUseCases presenceUseCases(PresenceRepositoryGateway repositoryGateway, @Autowired UserUseCases userUseCases, @Autowired KitUseCases kitUseCases) {
        return new PresenceUseCases(repositoryGateway, userUseCases, kitUseCases);
    }

    @Bean
    PresenceRepositoryGatewayImpl presenceRepositoryGateway(PresenceRepository repository, PresenceMapper mapper) {
        return new PresenceRepositoryGatewayImpl(repository, mapper);
    }

}
