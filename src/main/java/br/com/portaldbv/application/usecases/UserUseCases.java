package br.com.portaldbv.application.usecases;

import br.com.portaldbv.application.gateways.UserRepositoryGateway;
import br.com.portaldbv.domain.entities.User;
import br.com.portaldbv.domain.enums.UserTypeEnum;
import br.com.portaldbv.domain.enums.error.ClubErrorEnum;
import br.com.portaldbv.domain.enums.error.UnitErrorEnum;
import br.com.portaldbv.domain.enums.error.UserErrorEnum;
import br.com.portaldbv.domain.exceptions.DomainException;
import io.micrometer.common.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

public class UserUseCases {

    private final UserRepositoryGateway repository;
    private final ClubUseCases clubUseCases;
    private final UnitUseCases unitUseCases;

    public UserUseCases(UserRepositoryGateway repository, ClubUseCases clubUseCases, UnitUseCases unitUseCases) {
        this.repository = repository;
        this.clubUseCases = clubUseCases;
        this.unitUseCases = unitUseCases;
    }

    public List<User> getAllByClub(Long clubId, Boolean onlyActives, Boolean onlyUsersWithCashValue, List<UserTypeEnum> userTypeList) {
        return Optional.ofNullable(repository.getAllByClubId(clubId,
                        (onlyActives != null ? onlyActives : Boolean.TRUE),
                        (onlyUsersWithCashValue != null ? onlyUsersWithCashValue : Boolean.FALSE),
                        (userTypeList != null ? userTypeList : EnumSet.allOf(UserTypeEnum.class).stream().toList())))
                .orElseThrow(() -> new DomainException(ClubErrorEnum.ID_NOT_FOUND));
    }

    public User getById(UUID id) {
        return Optional.ofNullable(repository.getById(id))
                .orElseThrow(() -> new DomainException(UserErrorEnum.ID_NOT_FOUND));
    }

    public User register(User user, Long clubId, Long unitId) {

        cpfValidate(user);

        user.setClub(clubUseCases.getById(clubId));

        validateCredentials(user);
        if (unitId != null) {
            var unit = unitUseCases.getById(unitId);
            if (!Objects.equals(unit.getClubId(), clubId)) throw new DomainException(UnitErrorEnum.INVALID_CLUB);
            user.setUnit(unit);
        }

        user.setActive(Boolean.TRUE);
        user.setBank(BigDecimal.ZERO);

        return repository.register(user);
    }

    private void cpfValidate(User user) {
        if (repository.getByCpf(user.getCpf()) != null) throw new DomainException(UserErrorEnum.ALREADY_REGISTERED);
    }

    public User update(UUID id, User user, Long unitId) {
        var oldUser = getById(id);

        if (!StringUtils.isBlank(user.getName())) oldUser.setName(user.getName());
        if (!StringUtils.isBlank(user.getCpf()) && !oldUser.getCpf().equals(user.getCpf())) {
            cpfValidate(user);
            oldUser.setCpf(user.getCpf());
        }
        if (user.getBirthDate() != null) oldUser.setBirthDate(user.getBirthDate());
        if (user.getGender() != null) oldUser.setGender(user.getGender());
        if (user.getActive() != null) oldUser.setActive(user.getActive());
        if (user.getUserType() != null) oldUser.setUserType(user.getUserType());

        validateCredentials(user);
        if (!StringUtils.isBlank(user.getEmail())) oldUser.setEmail(user.getEmail());
        if (!StringUtils.isBlank(user.getPassword())) oldUser.setPassword(user.getPassword());

        if (unitId != null) {
            var unit = unitUseCases.getById(unitId);
            if (!Objects.equals(unit.getClubId(), oldUser.getClub().getId())) {
                throw new DomainException(UnitErrorEnum.INVALID_CLUB);
            } else {
                oldUser.setUnit(unit);
            }
        } else {
            oldUser.setUnit(null);
        }

        return repository.update(oldUser);
    }

    public void depositAmount(UUID id, BigDecimal amount) {
        User user = this.getById(id);
        user.setBank(user.getBank().add(amount));
        repository.update(user);
    }

    public void withdrawAmount(UUID id, BigDecimal amount) {
        User user = this.getById(id);
        user.setBank(user.getBank().subtract(amount));
        repository.update(user);
    }

    public void delete(UUID id) {
        repository.delete(getById(id));
    }

    private static void validateCredentials(User user) {
        if (EnumSet.of(UserTypeEnum.EXECUTIVE, UserTypeEnum.DIRECTION).contains(user.getUserType())) {
            if (StringUtils.isBlank(user.getEmail()) || user.getEmail().length() < 5 || user.getEmail().length() > 16) {
                throw new DomainException(UserErrorEnum.INVALID_USER);
            }

            if (StringUtils.isBlank(user.getPassword()) || user.getPassword().length() < 5 || user.getPassword().length() > 16) {
                throw new DomainException(UserErrorEnum.INVALID_PASSWORD);
            }
        } else {
            user.setEmail(null);
            user.setPassword(null);
        }
    }

}
