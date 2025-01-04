package br.com.portaldbv.infra.controller;

import br.com.portaldbv.application.usecases.UserUseCases;
import br.com.portaldbv.domain.enums.UserTypeEnum;
import br.com.portaldbv.infra.dto.request.AmountRequest;
import br.com.portaldbv.infra.dto.request.user.UserRequestDTO;
import br.com.portaldbv.infra.mapper.UserMapper;
import br.com.portaldbv.infra.resource.UserResource;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserController implements UserResource {

    private final UserUseCases useCases;
    private final UserMapper mapper;

    @Override
    public ResponseEntity<Object> getAllByClubId(Long clubId, Boolean onlyActives, Boolean onlyUsersWithCashValue, List<UserTypeEnum> userTypeList) {
        var users = useCases.getAllByClub(clubId, onlyActives, onlyUsersWithCashValue, userTypeList);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toReponseList(users));
    }

    @Override
    public ResponseEntity<Object> getById(UUID id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(useCases.getById(id)));
    }

    @Override
    public ResponseEntity<Object> register(Long clubId, UserRequestDTO userRequest) throws JsonProcessingException {
        var user = useCases.register(mapper.toDomain(userRequest), clubId, userRequest.unitId());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(user));
    }

    @Override
    public ResponseEntity<Object> update(UUID id, UserRequestDTO userRequest) throws JsonProcessingException {
        var user = useCases.update(id, mapper.toDomain(userRequest), userRequest.unitId());
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toResponse(user));
    }

    @Override
    public ResponseEntity<Object> depositAmount(UUID id, AmountRequest request) {
        useCases.depositAmount(id, request.amount());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Object> withdrawAmount(UUID id, AmountRequest request) {
        useCases.withdrawAmount(id, request.amount());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Object> delete(UUID id) {
        useCases.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}