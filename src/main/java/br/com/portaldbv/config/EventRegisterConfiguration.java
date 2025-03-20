package br.com.portaldbv.config;

import br.com.portaldbv.application.gateways.EventRegisterRepositoryGateway;
import br.com.portaldbv.application.usecases.EventRegisterUseCases;
import br.com.portaldbv.application.usecases.EventUseCases;
import br.com.portaldbv.application.usecases.UserUseCases;
import br.com.portaldbv.infra.gateway.EventRegisterRepositoryGatewayImpl;
import br.com.portaldbv.infra.mapper.EventRegisterMapper;
import br.com.portaldbv.infra.persistence.repository.EventRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventRegisterConfiguration {

    @Autowired
    private EventUseCases eventUseCases;

    @Autowired
    private UserUseCases userUseCases;

    @Bean
    EventRegisterUseCases eventRegisterUseCases(EventRegisterRepositoryGateway repositoryGateway) {
        return new EventRegisterUseCases(repositoryGateway, userUseCases, eventUseCases);
    }

    @Bean
    EventRegisterRepositoryGatewayImpl eventRegisterRepositoryGateway(EventRegisterRepository repository, EventRegisterMapper mapper) {
        return new EventRegisterRepositoryGatewayImpl(repository, mapper);
    }

}
