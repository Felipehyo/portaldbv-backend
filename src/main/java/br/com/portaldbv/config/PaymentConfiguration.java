package br.com.portaldbv.config;

import br.com.portaldbv.application.gateways.PaymentRepositoryGateway;
import br.com.portaldbv.application.usecases.*;
import br.com.portaldbv.infra.gateway.PaymentRepositoryGatewayImpl;
import br.com.portaldbv.infra.mapper.PaymentMapper;
import br.com.portaldbv.infra.persistence.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfiguration {

    @Autowired
    private ClubUseCases clubUseCases;

    @Autowired
    private EventUseCases eventUseCases;

    @Autowired
    private EventRegisterUseCases eventRegisterUseCases;

    @Autowired
    private UserUseCases userUseCases;

    @Bean
    PaymentUseCases paymentUseCases(PaymentRepositoryGateway repositoryGateway) {
        return new PaymentUseCases(repositoryGateway, clubUseCases, eventUseCases, eventRegisterUseCases, userUseCases);
    }

    @Bean
    PaymentRepositoryGatewayImpl paymentRepositoryGateway(PaymentRepository repository, PaymentMapper mapper) {
        return new PaymentRepositoryGatewayImpl(repository, mapper);
    }

}
