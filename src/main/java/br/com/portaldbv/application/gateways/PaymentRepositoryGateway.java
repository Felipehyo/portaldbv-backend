package br.com.portaldbv.application.gateways;

import br.com.portaldbv.domain.entities.Payment;
import br.com.portaldbv.infra.dto.PaginatedResponse;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PaymentRepositoryGateway {

    PaginatedResponse<Payment> getAllByClubWithFilters(Long clubId, LocalDate initialDate, LocalDate finalDate, UUID userId, Long eventId, Integer page, Integer size);

    Payment getById(UUID id);

    Payment register(Payment payment);

    void delete(Payment payment);

}
