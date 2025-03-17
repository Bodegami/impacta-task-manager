package br.com.bodegami.task_manager.domain.mapper;

import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskRequestDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskResponseDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.TaskDetailsResponse;
import br.com.bodegami.task_manager.application.entrypoint.dto.TaskResponseDTO;
import br.com.bodegami.task_manager.domain.entity.Task;
import com.fasterxml.uuid.Generators;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Mapper(componentModel = "spring", imports = {Generators.class})
public interface TaskMapper {

    @Mapping(target = "id", expression = "java(Generators.timeBasedEpochGenerator().generate())")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "dueDate", source = "dueDate", qualifiedByName = "parseDueDate")
    @Mapping(source = "userId", target = "user.id")
    Task toDomain(CreateTaskRequestDTO request);

    @Mapping(source = "user.id", target = "userId")
    CreateTaskResponseDTO toCreateResponse(Task task);

    TaskResponseDTO toFindAllResponse(Task task);

    @Mapping(source = "user.id", target = "userId")
    TaskDetailsResponse toTaskDetailsResponse(Task task);

    @Named("parseDueDate")
    default LocalDateTime parseDueDate(String dueDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        try {
            return LocalDateTime.parse(dueDate, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dueDate);
        }
    }

}
