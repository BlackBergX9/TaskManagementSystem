package org.blackbergx9.taskmanagementsystem.service;

import org.blackbergx9.taskmanagementsystem.dto.request.CreateTaskRequestDto;
import org.blackbergx9.taskmanagementsystem.dto.request.UpdateTaskRequestDto;
import org.blackbergx9.taskmanagementsystem.dto.res.CreateTaskResponseDto;
import org.blackbergx9.taskmanagementsystem.dto.res.GetAllTaskResponseDto;
import org.blackbergx9.taskmanagementsystem.dto.res.GetTaskResponseDto;
import org.blackbergx9.taskmanagementsystem.dto.res.UpdateTaskResponseDto;
import org.blackbergx9.taskmanagementsystem.entity.Task;
import org.blackbergx9.taskmanagementsystem.exception.ResourceNotFoundException;
import org.blackbergx9.taskmanagementsystem.mapper.TaskMapper;
import org.blackbergx9.taskmanagementsystem.repository.TaskRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

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

        Task taskEntity = taskRepository
                .findById(taskId)
                .orElseThrow( () ->
                        new ResourceNotFoundException("No task found with id " + taskId) );

        return new TaskMapper().toGetTaskResponseDto(taskEntity);
    }


    public List<GetAllTaskResponseDto> getTaskList(int page, int limit, String search) {

        TaskMapper taskMapper = new TaskMapper();

        return
        taskRepository
                .findByTitleContainingIgnoreCase(search, PageRequest.of(
                        page,
                        limit,
                        Sort.by("id").ascending().and( Sort.by("title").ascending() )
                        ))
                .stream()
                .map(taskMapper::toGetAllTaskResponseDto)
                .toList();

    }

    public void deleteTask(Long taskId) {

        Task task = taskRepository
                .findById(taskId)
                .orElseThrow( () -> new ResourceNotFoundException("No task found with id " + taskId) );

        taskRepository.delete(task);

    }

    public UpdateTaskResponseDto updateTaskById(UpdateTaskRequestDto updateTaskDto, Long taskId) {

        // TODO: Simple the process

        Task dbTask = taskRepository
                .findById(taskId)
                .orElseThrow( () ->
                        new ResourceNotFoundException("No task found with id " + taskId) );


            Task taskUpdates = new TaskMapper().toTaskEntity(updateTaskDto);

            // TODO: Use Builder
            dbTask.setTitle(
                    validateValue(dbTask.getTitle(), taskUpdates.getTitle()) );

            dbTask.setDescription(
                        validateValue(dbTask.getDescription(), taskUpdates.getDescription()) );

            dbTask.setPriority(
                        validateValue(dbTask.getPriority(), taskUpdates.getPriority()) );

            dbTask.setStatus(
                        validateValue( dbTask.getStatus(), taskUpdates.getStatus() ) );

            dbTask.setDueDate(
                        validateValue( dbTask.getDueDate(), taskUpdates.getDueDate() ) );

            dbTask.setUpdatedAt(Instant.now());


            dbTask.setAssignee(dbTask.getAssignee()); // Non-Repudiation.
            Task saved = taskRepository.save(dbTask);


        return new TaskMapper().toUpdateTaskResponseDto(saved);

    }


    private <T> T validateValue(T currentValue, T newValue)
    {
        return (newValue == null || newValue.toString().isBlank()) ? currentValue : newValue;
    }
}
