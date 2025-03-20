package br.com.portaldbv.infra.controller;

import br.com.portaldbv.application.usecases.EventRegisterUseCases;
import br.com.portaldbv.application.usecases.EventUseCases;
import br.com.portaldbv.domain.enums.UserTypeEnum;
import br.com.portaldbv.infra.dto.request.club.BankRequestDTO;
import br.com.portaldbv.infra.dto.request.event.EventRequestDTO;
import br.com.portaldbv.infra.dto.request.event.UserRegisterDTO;
import br.com.portaldbv.infra.mapper.EventMapper;
import br.com.portaldbv.infra.resource.EventResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EventController implements EventResource {

    private final EventUseCases useCases;
    private final EventRegisterUseCases eventRegisterUseCases;
    private final EventMapper mapper;

    @Override
    public ResponseEntity<Object> getAllByClubId(Long clubId, Boolean onlyActives) {
        return ResponseEntity.status(HttpStatus.OK).body(useCases.getAllByClub(clubId, onlyActives));
    }

    @Override
    public ResponseEntity<Object> getById(Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(useCases.getById(id)));
    }

    @Override
    public ResponseEntity<Object> register(EventRequestDTO request, Long clubId) {
        var domain = useCases.register(mapper.toDomain(request), clubId);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(domain));
    }

    @Override
    public ResponseEntity<Object> update(Long id, EventRequestDTO request) {
        var domain = useCases.update(id, mapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toDTO(domain));
    }

    @Override
    public ResponseEntity<Object> delete(Long id) {
        useCases.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Object> deposit(Long id, BankRequestDTO bankRequest) {
        useCases.deposit(id, bankRequest.value());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Object> withdraw(Long id, BankRequestDTO bankRequest) {
        useCases.subtract(id, bankRequest.value());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Object> status(Long id, Boolean activate) {
        useCases.activeOrInactive(id, activate);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Object> registerUserInEvent(Long id, UserRegisterDTO request) {
        eventRegisterUseCases.subscribeOrUnsubscribeUser(request.userId(), id, request.registerType());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Object> getAllRegistersByEvent(Long id, List<UserTypeEnum> userTypeList) {
        var response = eventRegisterUseCases.getAllEventRegistersFilterByUserType(id, userTypeList);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<Object> getSubscribeList(Long id) {
        var response = eventRegisterUseCases.getAllUsersWithSubscribeStatus(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}