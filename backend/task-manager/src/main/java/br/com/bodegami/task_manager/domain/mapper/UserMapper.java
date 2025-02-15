package br.com.bodegami.task_manager.domain.mapper;

import br.com.bodegami.task_manager.application.entrypoint.dto.CreateUserRequestDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.CreateUserResponseDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.UserDetailsResponseDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.UserResponseDTO;
import br.com.bodegami.task_manager.domain.entity.User;
import com.fasterxml.uuid.Generators;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {Generators.class})
public interface UserMapper {

    @Mapping(target = "id", expression = "java(Generators.timeBasedEpochGenerator().generate())")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    User toDomain(CreateUserRequestDTO request);

    CreateUserResponseDTO toCreateResponse(User user);

    UserResponseDTO toUserResponse(User user);

    UserDetailsResponseDTO toUserDetailsResponse(User user);



}
