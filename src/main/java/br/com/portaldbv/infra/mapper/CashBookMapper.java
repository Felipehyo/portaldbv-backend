package br.com.portaldbv.infra.mapper;

import br.com.portaldbv.domain.entities.CashBook;
import br.com.portaldbv.infra.dto.cashbook.CashBookRequestDTO;
import br.com.portaldbv.infra.dto.cashbook.CashBookResponseDTO;
import br.com.portaldbv.infra.persistence.entities.CashBookEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CashBookMapper {

    CashBookEntity toEntity(CashBook cashBook);

    CashBook toDomain(CashBookEntity cashBookEntity);

    CashBook toDomain(CashBookRequestDTO cashBookRequestDTO);

    CashBookResponseDTO toDTO(CashBook cashBook);

    List<CashBook> toDomainList(List<CashBookEntity> cashBookEntities);

    List<CashBookResponseDTO> toDTOList(List<CashBook> cashBooks);
}