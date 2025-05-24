package br.com.bodegami.task_manager.domain.mapper;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskCommentResponseDTO;
import br.com.bodegami.task_manager.domain.entity.Task;
import br.com.bodegami.task_manager.domain.entity.TaskComment;
import br.com.bodegami.task_manager.domain.entity.User;
import com.fasterxml.uuid.Generators;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {Generators.class, LocalDateTime.class})
public interface TaskCommentMapper {

    @Mapping(target = "id", expression = "java(Generators.timeBasedEpochGenerator().generate())")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "task", source = "task")
    @Mapping(target = "user", source = "user")
    TaskComment toDomain(String comment, User user, Task task);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "comment", source = "comment")
    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "createdAt", source = "createdAt")
    TaskCommentResponseDTO toResponseDTO(TaskComment taskComment);

}
