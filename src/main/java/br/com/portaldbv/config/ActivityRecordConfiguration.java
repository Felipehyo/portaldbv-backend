package br.com.portaldbv.config;

import br.com.portaldbv.application.gateways.ActivityRecordRepositoryGateway;
import br.com.portaldbv.application.gateways.ActivityRepositoryGateway;
import br.com.portaldbv.application.usecases.ActivityRecordUseCases;
import br.com.portaldbv.application.usecases.ActivityUseCases;
import br.com.portaldbv.application.usecases.UnitUseCases;
import br.com.portaldbv.application.usecases.UserUseCases;
import br.com.portaldbv.infra.gateway.ActivityRecordGatewayImpl;
import br.com.portaldbv.infra.mapper.ActivityRecordMapper;
import br.com.portaldbv.infra.persistence.repository.ActivityRecordRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActivityRecordConfiguration {

    @Bean
    ActivityRecordUseCases activityRecordUseCases(ActivityRecordRepositoryGateway repositoryGateway, ActivityRepositoryGateway activityRepository, UnitUseCases unitUseCases, UserUseCases userUseCases) {
        return new ActivityRecordUseCases(repositoryGateway, activityRepository, unitUseCases, userUseCases);
    }

//    @Bean
//    ActivityRecordGatewayImpl activityRecordGateway(ActivityRecordRepository repository, ActivityRecordMapper mapper) {
//        return new ActivityRecordGatewayImpl(repository, mapper);
//    }

}
