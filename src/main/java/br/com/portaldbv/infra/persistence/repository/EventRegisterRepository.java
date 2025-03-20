package br.com.portaldbv.infra.persistence.repository;

import br.com.portaldbv.domain.enums.UserTypeEnum;
import br.com.portaldbv.infra.persistence.entities.EventRegisterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRegisterRepository extends JpaRepository<EventRegisterEntity, Long> {

    Optional<EventRegisterEntity> getEventRegisterEntityByEventIdAndUserId(Long id, UUID userId);

    List<EventRegisterEntity> getEventRegisterEntityByEventIdAndUserTypeIn(Long id, List<UserTypeEnum> userTypes);

}
