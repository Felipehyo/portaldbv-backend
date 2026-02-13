package br.com.portaldbv.infra.mapper;

import br.com.portaldbv.domain.entities.User;
import br.com.portaldbv.domain.entities.VirtualMinutes;
import br.com.portaldbv.infra.dto.virtualminutes.VirtualMinutesRequestDTO;
import br.com.portaldbv.infra.dto.virtualminutes.VirtualMinutesResponseDTO;
import br.com.portaldbv.infra.persistence.entities.VirtualMinutesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface VirtualMinutesMapper {

    @Mapping(target = "imageLinks", ignore = true)
    VirtualMinutesEntity toEntity(VirtualMinutes virtualMinutes);

    @Mapping(target = "imageLinks", ignore = true)
    VirtualMinutes toDomain(VirtualMinutesEntity virtualMinutesEntity);

    VirtualMinutes toDomain(VirtualMinutesRequestDTO virtualMinutesRequestDTO);

    @Mapping(source = "unit.id", target = "unitId")
    @Mapping(source = "unit.name", target = "unitName")
    @Mapping(source = "createdBy.id", target = "createdByUserId")
    @Mapping(source = "createdBy.name", target = "createdByUserName")
    @Mapping(source = "presentUsers", target = "presentUserNames", qualifiedByName = "formatPresentUserNames")
    VirtualMinutesResponseDTO toDTO(VirtualMinutes virtualMinutes);


    List<VirtualMinutesResponseDTO> toDTOList(List<VirtualMinutes> virtualMinutesList);

    @Named("formatPresentUserNames")
    default List<String> formatPresentUserNames(List<User> users) {
        if (users == null) return null;
        return users.stream()
                .map(user -> {
                    if (user == null || user.getName() == null || user.getName().isBlank()) return null;
                    String[] parts = user.getName().trim().split("\\s+");
                    if (parts.length == 1) return parts[0];
                    String firstName = parts[0];
                    String firstSurname = parts[1];
                     // If first surname length <= 4, include second surname when available
                     if (firstSurname.length() <= 4 && parts.length > 2) {
                         return firstName + " " + firstSurname + " " + parts[2];
                     }
                     return firstName + " " + firstSurname;
                })
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toList());
    }
}
