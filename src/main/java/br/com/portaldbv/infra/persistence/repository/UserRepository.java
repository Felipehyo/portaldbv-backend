package br.com.portaldbv.infra.persistence.repository;

import br.com.portaldbv.domain.enums.UserTypeEnum;
import br.com.portaldbv.infra.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> getUserEntityById(UUID id);
    List<UserEntity> getUserEntityByUnitId(Long id);
    Optional<UserEntity> getUserEntityByCpf(String cpf);

    @Query("SELECT u FROM UserEntity u WHERE u.club.id = :clubId " +
            "AND (:onlyActives = FALSE OR u.active = TRUE) " +
            "AND (:userTypeEnum IS NULL OR u.userType IN :userTypeEnum) " +
            "AND (:onlyUsersWithCashValue = FALSE OR u.bank > 0)")
    List<UserEntity> findUserEntityByClubIdAndFilters(
            @Param("clubId") Long clubId,
            @Param("onlyActives") Boolean onlyActives,
            @Param("userTypeEnum") List<UserTypeEnum> userTypeEnum,
            @Param("onlyUsersWithCashValue") Boolean onlyUsersWithCashValue
    );
}
