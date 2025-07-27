package br.com.bodegami.task_manager.domain.mapper;

import br.com.bodegami.task_manager.application.entrypoint.dto.*;
import br.com.bodegami.task_manager.domain.entity.Task;
import com.fasterxml.uuid.Generators;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Mapper(componentModel = "spring", imports = {Generators.class})
public interface TaskMapper {

    String BACKLOG = "BACKLOG";

    @Mapping(target = "id", expression = "java(Generators.timeBasedEpochGenerator().generate())")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "dueDate", source = "request.dueDate", qualifiedByName = "parseDueDate")
    @Mapping(target = "user.id", source = "userIdFromToken")
    @Mapping(target = "status", constant = BACKLOG)
    Task toDomain(CreateTaskRequest request, String userIdFromToken);

    @Mapping(source = "user.id", target = "userId")
    CreateTaskResponse toCreateResponse(Task task);

    TaskResponse toFindAllResponse(Task task);

    @Mapping(source = "user.id", target = "userId")
    TaskDetailsResponse toTaskDetailsResponse(Task task);

    @Mapping(target = "dueDate", source = "request.dueDate", qualifiedByName = "parseDueDate")
    @Mapping(source = "title", target = "request.title")
    @Mapping(source = "description", target = "request.description")
    @Mapping(source = "status", target = "request.status")

    @Named("parseDueDate")
    default LocalDateTime parseDueDate(String dueDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        try {
            return LocalDateTime.parse(dueDate, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dueDate);
        }
    }

    @Mapping(source = "request.title", target = "task.title")
    @Mapping(source = "request.description", target = "task.description")
    @Mapping(source = "request.status", target = "task.status")
    @Mapping(source = "request.dueDate", target = "task.dueDate", qualifiedByName = "parseDueDate")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    Task toUpdateDomain(@MappingTarget Task task, UpdateTaskRequest request);
}
