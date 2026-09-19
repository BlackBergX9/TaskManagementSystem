package org.blackbergx9.taskmanagementsystem.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.blackbergx9.taskmanagementsystem.dto.request.CreateTaskRequestDto;
import org.blackbergx9.taskmanagementsystem.dto.request.UpdateTaskRequestDto;
import org.blackbergx9.taskmanagementsystem.dto.res.CreateTaskResponseDto;
import org.blackbergx9.taskmanagementsystem.dto.res.GetAllTaskResponseDto;
import org.blackbergx9.taskmanagementsystem.dto.res.GetTaskResponseDto;
import org.blackbergx9.taskmanagementsystem.dto.res.UpdateTaskResponseDto;
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

        // TODO: Make User Specific Task with Non-Repudiation...

        CreateTaskResponseDto res = taskService.createNewTask(taskDto);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<?> updateTask(

        @Positive @PathVariable Long taskId,
        @Valid @RequestBody UpdateTaskRequestDto taskUpdate
    ) {

        UpdateTaskResponseDto res = taskService.updateTaskById(taskUpdate, taskId);
        return new ResponseEntity<>(Map.of("updatedTask", res), HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(
        @Positive @PathVariable Long taskId
    ) {

        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTasks(

        @Positive @PathVariable Long taskId
    ) {

        GetTaskResponseDto task = taskService.getTaskById(taskId);
        return ResponseEntity.ok( Map.of("task", task) );
    }

   // TODO: Support: Pagination, Sorting, Search, Filtering
    @GetMapping
    public ResponseEntity<?> getAllTasks(
            @RequestParam(defaultValue = "0") float page,
            @RequestParam(defaultValue = "10") float limit,
            @RequestParam(defaultValue = "") String s
    ) {



        List<GetAllTaskResponseDto> taskList = taskService.getTaskList(
                (int)page,
                (int)limit,
                s
        );
        return new ResponseEntity<>(Map.of("tasks", taskList), HttpStatus.OK);
    }
}
