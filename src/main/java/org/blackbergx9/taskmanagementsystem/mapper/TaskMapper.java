package org.blackbergx9.taskmanagementsystem.mapper;

import org.blackbergx9.taskmanagementsystem.dto.request.CreateTaskRequestDto;
import org.blackbergx9.taskmanagementsystem.dto.request.UpdateTaskRequestDto;
import org.blackbergx9.taskmanagementsystem.dto.res.CreateTaskResponseDto;
import org.blackbergx9.taskmanagementsystem.dto.res.GetAllTaskResponseDto;
import org.blackbergx9.taskmanagementsystem.dto.res.GetTaskResponseDto;
import org.blackbergx9.taskmanagementsystem.dto.res.UpdateTaskResponseDto;
import org.blackbergx9.taskmanagementsystem.entity.Task;

import java.time.Instant;
// TODO: Use Builder...
public class TaskMapper {

    public Task toTaskEntity(CreateTaskRequestDto task) {

        Task taskEntity = new Task();

        taskEntity.setTitle(task.getTitle());
        taskEntity.setDescription( (task.getDescription() == null)? "" :  task.getDescription());
        taskEntity.setPriority(task.getPriority());
        taskEntity.setStatus(task.getStatus());
        taskEntity.setDueDate(task.getDueDate());
        taskEntity.setAssignee(task.getAssignee());

        Instant timeStamp = Instant.now();

        taskEntity.setCreatedAt(timeStamp);
        taskEntity.setUpdatedAt(null);  // So Update to Data can be shown as a UI Element. No element if found null.

        return taskEntity;

    }

    public Task toTaskEntity(UpdateTaskRequestDto task)
    {
        Task taskEntity = new Task();

        taskEntity.setTitle(task.getTitle());
        taskEntity.setDescription(task.getDescription());
        taskEntity.setPriority(task.getPriority());
        taskEntity.setStatus(task.getStatus());
        taskEntity.setDueDate(task.getDueDate());

        return taskEntity;
    }
    //------------------------------------------------

    public CreateTaskResponseDto toCreateTaskResponseDto(Task taskEntity) {

           CreateTaskResponseDto responseDto = new CreateTaskResponseDto();

           responseDto.setId(taskEntity.getId());
           responseDto.setTitle(taskEntity.getTitle());
           responseDto.setDescription(taskEntity.getDescription());
           responseDto.setPriority(taskEntity.getPriority());
           responseDto.setStatus(taskEntity.getStatus());
           responseDto.setDueDate(taskEntity.getDueDate());
           responseDto.setAssignee(taskEntity.getAssignee());

           return responseDto;

    }

    public GetAllTaskResponseDto toGetAllTaskResponseDto(Task taskEntity) {

        GetAllTaskResponseDto responseDto = new GetAllTaskResponseDto();

        responseDto.setId(taskEntity.getId());
        responseDto.setTitle(taskEntity.getTitle());
        responseDto.setDescription(taskEntity.getDescription());
        responseDto.setPriority(taskEntity.getPriority());
        responseDto.setStatus(taskEntity.getStatus());

        return  responseDto;
    }

    public GetTaskResponseDto toGetTaskResponseDto(Task task) {

        GetTaskResponseDto  responseDto = new GetTaskResponseDto();

        responseDto.setId(task.getId());

        responseDto.setTitle(task.getTitle());
        responseDto.setDescription(task.getDescription());
        responseDto.setAssignee(task.getAssignee());

        responseDto.setPriority(task.getPriority());
        responseDto.setStatus(task.getStatus());

        responseDto.setDueDate(task.getDueDate());

        responseDto.setCreatedAt(task.getCreatedAt());
        responseDto.setUpdatedAt(task.getUpdatedAt());

        return responseDto;

    }

    public UpdateTaskResponseDto toUpdateTaskResponseDto(Task saved) {

        UpdateTaskResponseDto responseDto = new UpdateTaskResponseDto();

        responseDto.setId(saved.getId());

        responseDto.setTitle(saved.getTitle());
        responseDto.setDescription(saved.getDescription());
        responseDto.setAssignee(saved.getAssignee());

        responseDto.setPriority(saved.getPriority());
        responseDto.setStatus(saved.getStatus());

        responseDto.setDueDate(saved.getDueDate());

        responseDto.setCreatedAt(saved.getCreatedAt());
        responseDto.setUpdatedAt(saved.getUpdatedAt());

        return responseDto;
    }
}
