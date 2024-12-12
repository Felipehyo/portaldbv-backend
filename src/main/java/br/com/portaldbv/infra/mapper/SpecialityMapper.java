package br.com.portaldbv.infra.mapper;

import br.com.portaldbv.domain.entities.Speciality;
import br.com.portaldbv.infra.dto.request.speciality.SpecialityRequestDTO;
import br.com.portaldbv.infra.dto.response.speciality.SpecialityResponseDTO;
import br.com.portaldbv.infra.persistence.entities.SpecialityEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpecialityMapper {

    SpecialityEntity toEntity(Speciality speciality);

    Speciality toDomain(SpecialityEntity specialityEntity);

    Speciality toDomain(SpecialityRequestDTO specialityRequestDTO);

    SpecialityResponseDTO toResponse(Speciality speciality);

    List<Speciality> toDomainList(List<SpecialityEntity> specialityEntities);

    List<SpecialityResponseDTO> toReponseList(List<Speciality> specialities);
}
