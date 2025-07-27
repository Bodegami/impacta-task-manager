package br.com.bodegami.task_manager.domain.mapper;

import br.com.bodegami.task_manager.application.entrypoint.dto.*;
import br.com.bodegami.task_manager.domain.entity.User;
import br.com.bodegami.task_manager.security.Role;
import br.com.bodegami.task_manager.security.RoleName;
import com.fasterxml.uuid.Generators;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", imports = {Generators.class})
public interface UserMapper {

    @Mapping(target = "id", expression = "java(Generators.timeBasedEpochGenerator().generate())")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "roles", source = "request", qualifiedByName = "mapRoles")
    @Mapping(target = "password", source = "encodedPassword")
    User toDomain(CreateUserRequest request, String encodedPassword);

    CreateUserResponse toCreateResponse(User user);

    UserResponse toUserResponse(User user);

    UserDetailsResponse toUserDetailsResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "user.email", source = "request.email")
    @Mapping(target = "user.name", source = "request.name")
    User toUpdateDomain(@MappingTarget User user, UpdateUserRequest request);

    @Named("mapRoles")
    default List<Role> mapRoles(CreateUserRequest request) {
        boolean isAdmin = request.role() != null && request.role().equalsIgnoreCase("ROLE_ADMINISTRATOR");

        Role role = (isAdmin) ? new Role(RoleName.ROLE_ADMINISTRATOR.getId(), RoleName.ROLE_ADMINISTRATOR):
                new Role(RoleName.ROLE_CUSTOMER.getId(), RoleName.ROLE_CUSTOMER);

        return Collections.singletonList(role);
    }

}
