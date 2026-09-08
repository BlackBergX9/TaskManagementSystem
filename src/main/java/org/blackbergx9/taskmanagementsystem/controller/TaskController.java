package org.blackbergx9.taskmanagementsystem.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.aspectj.lang.annotation.RequiredTypes;
import org.blackbergx9.taskmanagementsystem.dto.request.CreateTaskRequestDto;
import org.blackbergx9.taskmanagementsystem.dto.request.UpdateTaskRequestDto;
import org.blackbergx9.taskmanagementsystem.dto.res.CreateTaskResponseDto;
import org.blackbergx9.taskmanagementsystem.dto.res.GetAllTaskResponseDto;
import org.blackbergx9.taskmanagementsystem.dto.res.GetTaskResponseDto;
import org.blackbergx9.taskmanagementsystem.dto.res.UpdateTaskResponseDto;
import org.blackbergx9.taskmanagementsystem.entity.Task;
import org.blackbergx9.taskmanagementsystem.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    TaskController(TaskService taskService) {
        this.taskService = taskService;
    }



    @PostMapping()
    public ResponseEntity<?> createTask(

        @Valid @RequestBody
        CreateTaskRequestDto taskDto
    ) {
        CreateTaskResponseDto res = taskService.createNewTask(taskDto);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<?> updateTask(

        @Positive @PathVariable Long taskId
        , @Valid @RequestBody UpdateTaskRequestDto taskUpdate
    ) {
        UpdateTaskResponseDto res = taskService.updateTaskById(taskUpdate, taskId);

        if (res == null) return new ResponseEntity<>(Map.of("message", "Task Not found"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(Map.of("updatedTask", res), HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(
        @Positive @PathVariable Long taskId
    ) {
        boolean isDeleted = taskService.deleteTask(taskId);

        if (isDeleted) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTasks(

        @Positive @PathVariable Long taskId
    ) {
        GetTaskResponseDto task = taskService.getTaskById(taskId);
        if (task == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(Map.of("task", task), HttpStatus.OK);
    }

   // TODO: Support: Pagination, Sorting, Search, Filtering
    @GetMapping
    public ResponseEntity<?> getAllTasks() {

        List<GetAllTaskResponseDto> taskList = taskService.getTaskList();
        return new ResponseEntity<>(Map.of("tasks", taskList), HttpStatus.OK);
    }
}
