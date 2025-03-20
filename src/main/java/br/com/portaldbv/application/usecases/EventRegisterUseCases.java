package br.com.portaldbv.application.usecases;

import br.com.portaldbv.application.gateways.EventRegisterRepositoryGateway;
import br.com.portaldbv.domain.dto.EventRegisterDTO;
import br.com.portaldbv.domain.dto.UserEventRegisterDTO;
import br.com.portaldbv.domain.entities.Event;
import br.com.portaldbv.domain.entities.EventRegister;
import br.com.portaldbv.domain.entities.User;
import br.com.portaldbv.domain.enums.EventTypeRegisterEnum;
import br.com.portaldbv.domain.enums.UserTypeEnum;
import br.com.portaldbv.domain.enums.error.EventRegisterErrorEnum;
import br.com.portaldbv.domain.exceptions.DomainException;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@RequiredArgsConstructor
public class EventRegisterUseCases {

    private final EventRegisterRepositoryGateway repository;
    private final UserUseCases userUseCases;
    private final EventUseCases eventUseCases;

    public Optional<EventRegister> getEventRegisterByEventAndUser(Long eventId, UUID userId) {
        return repository.getByEventIdAndUserId(eventId, userId);
    }

    public List<EventRegisterDTO> getAllEventRegistersFilterByUserType(Long eventId, List<UserTypeEnum> userTypes) {

        if (userTypes == null || userTypes.isEmpty()) {
            userTypes = Arrays.stream(UserTypeEnum.values()).toList();
        }

        var registerList = repository.getAllByEventRegisterIdAndUserTypes(eventId, userTypes);

        ArrayList<EventRegisterDTO> responseList = new ArrayList<>();

        registerList.forEach(eventRegister ->
                responseList.add(EventRegisterDTO.builder()
                        .userId(eventRegister.getUser().getId())
                        .userName(eventRegister.getUser().getName())
                        .userType(eventRegister.getUser().getType())
                        .userGender(eventRegister.getUser().getGender())
                        .allocatedValue(eventRegister.getAllocatedValue())
                        .debtValue(eventRegister.getEvent().getValue().subtract(eventRegister.getAllocatedValue()))
                        .percentagePayment(eventRegister.getAllocatedValue().multiply(new BigDecimal("100.00")).divide(eventRegister.getEvent().getValue(), RoundingMode.UP).doubleValue())
                        .build())
        );

        return responseList;
    }

    public Integer countEventRegistersByEvent(Long eventId) {
        return repository.getAllByEventRegisterIdAndUserTypes(eventId, Arrays.stream(UserTypeEnum.values()).toList()).size();
    }

    public Optional<EventRegister> subscribeOrUnsubscribeUser(UUID userId, Long eventId, EventTypeRegisterEnum eventType) {

        var user = userUseCases.getById(userId);
        var event = eventUseCases.getById(eventId);

        var oldEventRegister = repository.getByEventIdAndUserId(eventId, userId);

        if (EventTypeRegisterEnum.SUBSCRIBE.equals(eventType)) {
            if (oldEventRegister.isPresent())
                throw new DomainException(EventRegisterErrorEnum.ALREADY_REGISTERED);

            var register = EventRegister.builder()
                    .user(user)
                    .event(event)
                    .allocatedValue(BigDecimal.ZERO)
                    .build();

            if (event.getValue().equals(user.getBank()) || event.getValue().compareTo(user.getBank()) > 0)
                register.setAllocatedValue(register.getAllocatedValue().add(user.getBank()));
            else
                register.setAllocatedValue(register.getAllocatedValue().add(event.getValue()));

            userUseCases.subtract(userId, register.getAllocatedValue());
            eventUseCases.deposit(eventId, register.getAllocatedValue());

            return Optional.of(repository.save(register));
        } else {
            if (oldEventRegister.isEmpty())
                throw new DomainException(EventRegisterErrorEnum.NO_REGISTERED);

            userUseCases.deposit(userId, oldEventRegister.get().getAllocatedValue());
            eventUseCases.subtract(eventId, oldEventRegister.get().getAllocatedValue());

            repository.delete(oldEventRegister.get());

            return Optional.empty();
        }
    }

    public List<UserEventRegisterDTO> getAllUsersWithSubscribeStatus(Long eventId) {

        var event = eventUseCases.getById(eventId);
        var eventRegisters = repository.getAllByEventRegisterIdAndUserTypes(eventId, Arrays.stream(UserTypeEnum.values()).toList());
        var users = userUseCases.getAllByClub(event.getClub().getId(), Boolean.TRUE, Boolean.FALSE, Arrays.stream(UserTypeEnum.values()).toList());

        var responseList = new ArrayList<UserEventRegisterDTO>();

        users.forEach(user -> {
            boolean subscribed = Boolean.FALSE;

            for (var er : eventRegisters) {
                if (er.getUser().getId().equals(user.getId())) {
                    subscribed = Boolean.TRUE;
                    break;
                }
            }

            responseList.add(UserEventRegisterDTO.builder()
                    .userId(user.getId())
                    .userName(user.getName())
                    .userGender(user.getGender())
                    .subscribe(subscribed)
                    .build());
        });

        return responseList;
    }

    public void allocateValueInEvent(BigDecimal value, EventRegister eventRegister, Event event, User user) {
        var debtorValue = BigDecimal.ZERO;

        if (eventRegister.getAllocatedValue().doubleValue() < event.getValue().doubleValue()) {
            debtorValue = debtorValue.add(event.getValue().subtract(eventRegister.getAllocatedValue()));//valor devedor alocado para total evento

            if (value.doubleValue() <= debtorValue.doubleValue()) {
                eventRegister.setAllocatedValue(eventRegister.getAllocatedValue().add(value));//add valor total do pagamento no registro de cadastro do evento
                event.setBank(event.getBank().add(value));
            } else {
                eventRegister.setAllocatedValue(eventRegister.getAllocatedValue().add(debtorValue));//adicionar valor restante total no registro de cadastro do evento
                user.setBank(user.getBank().add(value.subtract(debtorValue)));//add valor restante no caixa do usuário
                event.setBank(event.getBank().add(debtorValue));
            }

            repository.save(eventRegister);
        }
    }

}
