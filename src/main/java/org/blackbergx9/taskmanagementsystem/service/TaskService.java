package org.blackbergx9.taskmanagementsystem.service;

import jakarta.validation.constraints.Positive;
import org.blackbergx9.taskmanagementsystem.dto.request.CreateTaskRequestDto;
import org.blackbergx9.taskmanagementsystem.dto.request.UpdateTaskRequestDto;
import org.blackbergx9.taskmanagementsystem.dto.res.CreateTaskResponseDto;
import org.blackbergx9.taskmanagementsystem.dto.res.GetAllTaskResponseDto;
import org.blackbergx9.taskmanagementsystem.dto.res.GetTaskResponseDto;
import org.blackbergx9.taskmanagementsystem.dto.res.UpdateTaskResponseDto;
import org.blackbergx9.taskmanagementsystem.entity.Task;
import org.blackbergx9.taskmanagementsystem.mapper.TaskMapper;
import org.blackbergx9.taskmanagementsystem.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    //-----

    public CreateTaskResponseDto createNewTask(CreateTaskRequestDto task) {

        Task taskEntity = taskRepository
            .save(new TaskMapper().toTaskEntity(task));

        return new TaskMapper()
            .toCreateTaskResponseDto(taskEntity);
    }


    public GetTaskResponseDto getTaskById(Long  taskId) {

        Optional<Task> taskEntity = taskRepository.findById(taskId);

        return
        taskEntity
        . map(new TaskMapper()::toGetTaskResponseDto)
        . orElse(null)
        ;
    }


    public List<GetAllTaskResponseDto> getTaskList() {

        TaskMapper taskMapper = new TaskMapper();

        return
        taskRepository
        . findAll()
        . stream()
        . map(taskMapper::toGetAllTaskResponseDto)
        . toList()
        ;

    }

    public boolean deleteTask(Long taskId) {

        if (taskRepository.existsById(taskId)) {

            taskRepository.deleteById(taskId);
            return true;
        }

        else return false;
    }

    public UpdateTaskResponseDto updateTaskById(UpdateTaskRequestDto updateTask, @Positive Long taskId) {

        Task saved
        = taskRepository
        . findById(taskId)
        . map( dbTask -> {

            Task newTask = new TaskMapper().toTaskEntity(updateTask);

            dbTask
                .setTitle(
                    validateValue(dbTask.getTitle(), newTask.getTitle())
                );
            dbTask
                .setDescription(
                    validateValue(dbTask.getDescription(), newTask.getDescription())
                );
            dbTask
                .setPriority(
                    validateValue(dbTask.getPriority(), newTask.getPriority())
                );
            dbTask
                .setStatus(
                    validateValue(dbTask.getStatus(), newTask.getStatus())
                );
            dbTask
                .setDueDate(
                    validateValue(dbTask.getDueDate(), newTask.getDueDate())
                );
            dbTask
                .setUpdatedAt(Instant.now());


            dbTask.setAssignee(dbTask.getAssignee()); // Non-Repudiation.
            return taskRepository.save(dbTask);

        })
        . orElse(null);
        ;

        if (saved == null) return null;

        return new TaskMapper().toUpdateTaskResponseDto(saved);

    }


    private <T> T validateValue(T currentValue, T newValue)
    {
        return (newValue == null || newValue.toString().isBlank()) ? currentValue : newValue;
    }
}
