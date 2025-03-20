package br.com.portaldbv.config;

import br.com.portaldbv.application.gateways.EventRepositoryGateway;
import br.com.portaldbv.application.usecases.ClubUseCases;
import br.com.portaldbv.application.usecases.EventUseCases;
import br.com.portaldbv.infra.gateway.EventRepositoryGatewayImpl;
import br.com.portaldbv.infra.mapper.EventMapper;
import br.com.portaldbv.infra.persistence.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfiguration {

    @Autowired
    private ClubUseCases clubUseCases;

    @Bean
    EventUseCases eventUseCases(EventRepositoryGateway repositoryGateway) {
        return new EventUseCases(repositoryGateway, clubUseCases);
    }

    @Bean
    EventRepositoryGatewayImpl eventRepositoryGateway(EventRepository repository, EventMapper mapper) {
        return new EventRepositoryGatewayImpl(repository, mapper);
    }

}
