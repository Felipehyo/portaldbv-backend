package br.com.portaldbv.infra.gateway;

import br.com.portaldbv.application.gateways.UserRepositoryGateway;
import br.com.portaldbv.domain.entities.User;
import br.com.portaldbv.domain.enums.UserTypeEnum;
import br.com.portaldbv.infra.mapper.UserMapper;
import br.com.portaldbv.infra.persistence.entities.UserEntity;
import br.com.portaldbv.infra.persistence.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserRepositoryGatewayImpl implements UserRepositoryGateway {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    public UserRepositoryGatewayImpl(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public User getById(UUID id) {
        Optional<UserEntity> entity = userRepository.getUserEntityById(id);
        return entity.map(mapper::toDomain).orElse(null);
    }

    @Override
    public User getByCpf(String cpf) {
        Optional<UserEntity> entity = userRepository.getUserEntityByCpf(cpf);
        return entity.map(mapper::toDomain).orElse(null);
    }

    @Override
    public List<User> getAllByClubId(Long clubId, Boolean onlyActives, Boolean onlyUsersWithCashValue, List<UserTypeEnum> userTypes) {
        List<UserEntity> users = userRepository.findUserEntityByClubIdAndFilters(clubId, onlyActives, userTypes, onlyUsersWithCashValue);
        return mapper.toDomainList(users);
    }

    @Override
    public List<User> getByUnit(Long unitId) {
        List<UserEntity> users = userRepository.getUserEntityByUnitId(unitId);
        return mapper.toDomainList(users);
    }

    @Override
    public User register(User user) {
        UserEntity entity = mapper.toEntity(user);
        return mapper.toDomain(userRepository.save(entity));
    }

    @Override
    public User update(User user) {
        return mapper.toDomain(userRepository.save(mapper.toEntity(user)));
    }

    @Override
    public void delete(User user) {
        userRepository.delete(mapper.toEntity(user));
    }
}
