package br.com.bodegami.task_manager.domain.mapper;

import br.com.bodegami.task_manager.application.entrypoint.dto.*;
import br.com.bodegami.task_manager.domain.entity.User;
import com.fasterxml.uuid.Generators;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", imports = {Generators.class})
public interface UserMapper {

    @Mapping(target = "id", expression = "java(Generators.timeBasedEpochGenerator().generate())")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    User toDomain(CreateUserRequestDTO request);

    CreateUserResponseDTO toCreateResponse(User user);

    UserResponseDTO toUserResponse(User user);

    UserDetailsResponseDTO toUserDetailsResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "user.email", source = "request.email")
    @Mapping(target = "user.name", source = "request.name")
    User toUpdateDomain(@MappingTarget User user, UpdateUserRequestDTO request);



}
