package br.com.portaldbv.infra.controller;

import br.com.portaldbv.application.usecases.PresenceUseCases;
import br.com.portaldbv.domain.entities.Presence;
import br.com.portaldbv.infra.dto.request.presence.PresenceRequestDTO;
import br.com.portaldbv.infra.mapper.PresenceMapper;
import br.com.portaldbv.infra.resource.PresenceResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PresenceController implements PresenceResource {

    private final PresenceUseCases useCases;
    private final PresenceMapper mapper;

    @Override
    public ResponseEntity<Object> getAllByClubIdAndUserId(Long clubId, UUID userId) {
        var presences = useCases.getAllByClubIdOrUserId(clubId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toResponseList(presences));
    }

    @Override
    public ResponseEntity<Object> getAllByClubIdWithPercentage(Long id) {
        var presences = useCases.getAllWithPercentage(id);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toMetricsResponseList(presences));
    }

    @Override
    public ResponseEntity<Object> getAllByDay(Long clubId, LocalDate day) {
        var presences = useCases.getAllByDay(clubId, day);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toResponseList(presences));
    }

    @Override
    public ResponseEntity<Object> register(UUID userId, PresenceRequestDTO request) {
        Presence presence = useCases.register(userId, mapper.toDomain(request), request.date());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(presence));
    }

}