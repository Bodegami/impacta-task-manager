package br.com.bodegami.task_manager.infrastructure.warmup;

import br.com.bodegami.task_manager.infrastructure.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class WarmUpComponent implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(WarmUpComponent.class);
    private final TaskRepository taskRepository;

    public WarmUpComponent(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(org.springframework.boot.ApplicationArguments args) {
        logger.info("Iniciando WarmUp...");

        // Exemplo de consulta para aquecer o sistema
        try {
            UUID fakeUserId = UUID.randomUUID(); // Substitua por um ID válido, se necessário
            //UUID fakeUserId = UUID.fromString("0195c0d4-f914-754a-91b5-384a8433076f");
            taskRepository.findAllByUserId(fakeUserId);
        } catch (Exception e) {
            logger.warn("WarmUp falhou: {}", e.getMessage());
        }

        logger.info("WarmUp concluído.");
    }
}
