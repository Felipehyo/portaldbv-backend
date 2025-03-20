package br.com.portaldbv.application.usecases;

import br.com.portaldbv.application.gateways.PaymentRepositoryGateway;
import br.com.portaldbv.domain.entities.Payment;
import br.com.portaldbv.domain.enums.EventTypeRegisterEnum;
import br.com.portaldbv.domain.enums.error.PaymentErrorEnum;
import br.com.portaldbv.domain.exceptions.DomainException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class PaymentUseCases {

    private final PaymentRepositoryGateway repository;
    private final ClubUseCases clubUseCases;
    private final EventUseCases eventUseCases;
    private final EventRegisterUseCases eventRegisterUseCases;
    private final UserUseCases userUseCases;

    public List<Payment> getAllByClubWithFilters(Long clubId, LocalDate initialDate, LocalDate finalDate, UUID userId, Long eventId, Integer page, Integer size) {
        return repository.getAllByClubWithFilters(clubId, initialDate, finalDate, userId, eventId, page, size);
    }

    public Payment getById(UUID id) {
        return Optional.ofNullable(repository.getById(id))
                .orElseThrow(() -> new DomainException(PaymentErrorEnum.ID_NOT_FOUND));
    }

    public Payment register(Payment payment, Long clubId, UUID userId, Long eventId) {

        var club = clubUseCases.getById(clubId);
        var user = userUseCases.getById(userId);

        clubUseCases.deposit(clubId, payment.getValue());

        if (eventId != null) {
            var event = eventUseCases.getById(eventId);

            var optionalEventRegister = eventRegisterUseCases.getEventRegisterByEventAndUser(eventId, userId);

            if (optionalEventRegister.isEmpty()) {
                optionalEventRegister = eventRegisterUseCases.subscribeOrUnsubscribeUser(userId, eventId, EventTypeRegisterEnum.SUBSCRIBE);
            }

            optionalEventRegister.ifPresent(eventRegister -> eventRegisterUseCases.allocateValueInEvent(payment.getValue(), eventRegister, event, user));

            payment.setEvent(event);
        } else {
            userUseCases.deposit(userId, payment.getValue());
        }

        payment.setClub(club);
        payment.setUser(user);

        return repository.register(payment);
    }

    public void delete(UUID id) {

        var payment = getById(id);

        if (payment.getValue().compareTo(payment.getUser().getBank()) > 0) {
            throw new DomainException(PaymentErrorEnum.INSUFFICIENT_BALANCE);
        }

        userUseCases.subtract(payment.getUser().getId(), payment.getValue());
        clubUseCases.subtract(payment.getClub().getId(), payment.getValue());

        repository.delete(payment);
    }

}
