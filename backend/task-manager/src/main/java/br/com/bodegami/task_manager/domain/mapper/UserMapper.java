package br.com.bodegami.task_manager.mapper;

import br.com.bodegami.task_manager.controller.dto.CreateUserRequestDTO;
import br.com.bodegami.task_manager.controller.dto.CreateUserResponseDTO;
import br.com.bodegami.task_manager.domain.entity.User;
import com.fasterxml.uuid.Generators;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {Generators.class})
public interface UserMapper {

    @Mapping(target = "id", expression = "java(Generators.timeBasedEpochGenerator().generate())")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    User toDomain(CreateUserRequestDTO request);

    CreateUserResponseDTO toDTO(User user);

}
