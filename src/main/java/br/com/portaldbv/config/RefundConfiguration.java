package br.com.portaldbv.config;

import br.com.portaldbv.application.gateways.RefundRepositoryGateway;
import br.com.portaldbv.application.usecases.ClubUseCases;
import br.com.portaldbv.application.usecases.EventUseCases;
import br.com.portaldbv.application.usecases.RefundUseCases;
import br.com.portaldbv.application.usecases.UserUseCases;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RefundConfiguration {

    @Bean
    RefundUseCases refundUseCases(RefundRepositoryGateway repositoryGateway, ClubUseCases clubUseCases, EventUseCases eventUseCases, UserUseCases userUseCases) {
        return new RefundUseCases(repositoryGateway, clubUseCases, eventUseCases, userUseCases);
    }

//    @Bean
//    ActivityRepositoryGatewayImpl activityRepositoryGateway(ActivityRepository repository, ActivityMapper mapper) {
//        return new ActivityRepositoryGatewayImpl(repository, mapper);
//    }

}
