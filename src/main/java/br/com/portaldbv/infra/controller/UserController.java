package br.com.portaldbv.infra.controller;

import br.com.portaldbv.application.usecases.UserUseCases;
import br.com.portaldbv.domain.enums.UserTypeEnum;
import br.com.portaldbv.infra.dto.user.AmountRequestDTO;
import br.com.portaldbv.infra.dto.user.LoginRequestDTO;
import br.com.portaldbv.infra.dto.user.UserRequestDTO;
import br.com.portaldbv.infra.dto.user.LoginResponseDTO;
import br.com.portaldbv.infra.dto.user.PasswordChangeRequestDTO;
import br.com.portaldbv.infra.mapper.UserMapper;
import br.com.portaldbv.infra.resource.UserResource;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserController implements UserResource {

    private final UserUseCases useCases;
    private final UserMapper mapper;

    @Override
    public ResponseEntity<Object> getAllByClubId(Long clubId, Long unitId, Boolean onlyActives, Boolean onlyUsersWithCashValue, List<UserTypeEnum> userTypeList) {
        var users = useCases.getAllByClub(clubId, unitId, onlyActives, onlyUsersWithCashValue, userTypeList);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toReponseList(users));
    }

    @Override
    public ResponseEntity<Object> getById(UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toResponse(useCases.getById(id)));
    }

    @Override
    public ResponseEntity<LoginResponseDTO> doLogin(LoginRequestDTO userRequest) {
        var user = useCases.doLogin(userRequest.email(), userRequest.password());
        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO(user.getId(), user.getType(), user.getClub().getId(), user.getUnit().getId()));
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
    public ResponseEntity<Object> changePassword(UUID id, PasswordChangeRequestDTO request) {
        useCases.changePassword(id, request.currentPassword(), request.newPassword());
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "PASSWORD_UPDATED"));
    }

    @Override
    public ResponseEntity<Object> depositAmount(UUID id, AmountRequestDTO request) {
        useCases.deposit(id, request.amount());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Object> withdrawAmount(UUID id, AmountRequestDTO request) {
        useCases.subtract(id, request.amount());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Object> delete(UUID id) {
        useCases.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}