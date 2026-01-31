package br.com.portaldbv.infra.persistence.repository;

import br.com.portaldbv.infra.persistence.entities.ActivityEntity;
import br.com.portaldbv.infra.persistence.entities.CashBookEntity;
import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActivityRepository extends JpaRepository<ActivityEntity, Long> {

    Optional<ActivityEntity> findByName(String name);

    List<ActivityEntity> findByClubId(Long clubId);

}