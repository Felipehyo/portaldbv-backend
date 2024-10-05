package br.com.portaldbv.infra.mapper;

import br.com.portaldbv.domain.entities.Club;
import br.com.portaldbv.infra.dto.request.ClubRequestDTO;
import br.com.portaldbv.infra.dto.response.ClubResponseDTO;
import br.com.portaldbv.infra.persistence.entities.ClubEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClubMapper {

    ClubEntity toEntity(Club club);

    Club toDomain(ClubEntity clubEntity);

    Club toDomain(ClubRequestDTO clubRequestDTO);

    ClubResponseDTO paraDTO(Club club);
}
