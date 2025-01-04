package br.com.portaldbv.infra.mapper;

import br.com.portaldbv.domain.entities.User;
import br.com.portaldbv.infra.dto.request.user.UserRequestDTO;
import br.com.portaldbv.infra.dto.response.user.UserResponseDTO;
import br.com.portaldbv.infra.persistence.entities.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toEntity(User user);

    User toDomain(UserEntity userEntity);

    User toDomain(UserRequestDTO userRequestDTO);

    UserResponseDTO toResponse(User user);

    List<User> toDomainList(List<UserEntity> userEntities);

    List<UserResponseDTO> toReponseList(List<User> users);
}
