package br.com.portaldbv.infra.mapper;

import br.com.portaldbv.domain.entities.Payment;
import br.com.portaldbv.infra.dto.request.payment.PaymentRequestDTO;
import br.com.portaldbv.infra.dto.response.payment.PaymentDetailResponseDTO;
import br.com.portaldbv.infra.dto.response.payment.PaymentResponseDTO;
import br.com.portaldbv.infra.persistence.entities.PaymentEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentEntity toEntity(Payment payment);

    Payment toDomain(PaymentEntity paymentEntity);

    Payment toDomain(PaymentRequestDTO paymentRequestDTO);

    PaymentResponseDTO toResponse(Payment payment);
    PaymentDetailResponseDTO toDetailResponse(Payment payment);

    List<Payment> toDomainList(List<PaymentEntity> paymentEntities);

    List<PaymentDetailResponseDTO> toReponseList(List<Payment> payments);
}
