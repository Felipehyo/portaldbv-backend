package br.com.portaldbv.config;

import br.com.portaldbv.application.gateways.ActivityRepositoryGateway;
import br.com.portaldbv.application.usecases.ActivityRecordUseCases;
import br.com.portaldbv.application.usecases.ActivityUseCases;
import br.com.portaldbv.application.usecases.ClubUseCases;
import br.com.portaldbv.application.usecases.UnitUseCases;
import br.com.portaldbv.infra.gateway.ActivityRepositoryGatewayImpl;
import br.com.portaldbv.infra.mapper.ActivityMapper;
import br.com.portaldbv.infra.persistence.repository.ActivityRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActivityConfiguration {

    @Bean
    ActivityUseCases activityUseCases(ActivityRepositoryGateway repositoryGateway, ClubUseCases clubUseCases, UnitUseCases unitUseCases, ActivityRecordUseCases activityRecordUseCases) {
        return new ActivityUseCases(repositoryGateway, clubUseCases, unitUseCases, activityRecordUseCases);
    }

//    @Bean
//    ActivityRepositoryGatewayImpl activityRepositoryGateway(ActivityRepository repository, ActivityMapper mapper) {
//        return new ActivityRepositoryGatewayImpl(repository, mapper);
//    }

}
