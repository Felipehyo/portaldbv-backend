package br.com.portaldbv.application.gateways;

import br.com.portaldbv.domain.entities.User;
import br.com.portaldbv.domain.enums.UserTypeEnum;

import java.util.List;
import java.util.UUID;

public interface UserRepositoryGateway {

    User getById(UUID id);
    User getByCpf(String cpf);

    List<User> getAllByClubId(Long clubId, Boolean onlyActives, Boolean onlyUsersWithCashValue, List<UserTypeEnum> userTypeEnum);

    List<User> getByUnit(Long unitId);

    User register(User user);

    User update(User user);

    void delete(User user);

}
