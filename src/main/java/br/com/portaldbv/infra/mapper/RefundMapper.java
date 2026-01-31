package br.com.portaldbv.infra.mapper;

import br.com.portaldbv.domain.entities.Refund;
import br.com.portaldbv.infra.dto.refund.RefundRequestDTO;
import br.com.portaldbv.infra.persistence.entities.RefundEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RefundMapper {

    RefundEntity toEntity(Refund cashBook);

    Refund toDomain(RefundEntity refundEntity);

    Refund toDomain(RefundRequestDTO refundRequest);

//    RefundResponseDTO toDTO(Refund cashBook);

    List<Refund> toDomainList(List<RefundEntity> cashBookEntities);

//    List<RefundResponseDTO> toDTOList(List<Refund> cashBooks);
}