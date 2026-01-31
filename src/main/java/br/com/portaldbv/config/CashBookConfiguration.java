package br.com.portaldbv.config;

import br.com.portaldbv.application.gateways.CashBookRepositoryGateway;
import br.com.portaldbv.application.usecases.CashBookUseCases;
import br.com.portaldbv.application.usecases.ClubUseCases;
import br.com.portaldbv.application.usecases.EventUseCases;
import br.com.portaldbv.infra.gateway.CashBookGatewayImpl;
import br.com.portaldbv.infra.mapper.CashBookMapper;
import br.com.portaldbv.infra.persistence.repository.CashBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CashBookConfiguration {

    @Autowired
    private ClubUseCases clubUseCases;

    @Autowired
    private EventUseCases eventUseCases;

    @Bean
    CashBookUseCases cashBookUseCases(CashBookRepositoryGateway repositoryGateway) {
        return new CashBookUseCases(repositoryGateway, clubUseCases, eventUseCases);
    }

//    @Bean
//    CashBookGatewayImpl cashBookGateway(CashBookRepository repository, CashBookMapper mapper) {
//        return new CashBookGatewayImpl(repository, mapper);
//    }

}
