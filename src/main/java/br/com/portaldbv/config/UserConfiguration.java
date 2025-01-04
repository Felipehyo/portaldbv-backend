package br.com.portaldbv.config;

import br.com.portaldbv.application.gateways.UserRepositoryGateway;
import br.com.portaldbv.application.usecases.ClubUseCases;
import br.com.portaldbv.application.usecases.UnitUseCases;
import br.com.portaldbv.application.usecases.UserUseCases;
import br.com.portaldbv.infra.gateway.UserRepositoryGatewayImpl;
import br.com.portaldbv.infra.mapper.UserMapper;
import br.com.portaldbv.infra.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {

    @Autowired
    private ClubUseCases clubUseCases;

    @Autowired
    private UnitUseCases unitUseCases;

    @Bean
    UserUseCases userUseCases(UserRepositoryGateway repositoryGateway) {
        return new UserUseCases(repositoryGateway, clubUseCases, unitUseCases);
    }

    @Bean
    UserRepositoryGatewayImpl userRepositoryGateway(UserRepository repository, UserMapper mapper) {
        return new UserRepositoryGatewayImpl(repository, mapper);
    }

}
