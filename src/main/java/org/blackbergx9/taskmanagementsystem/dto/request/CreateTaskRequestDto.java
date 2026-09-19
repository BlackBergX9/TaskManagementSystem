package org.blackbergx9.taskmanagementsystem.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.blackbergx9.taskmanagementsystem.util.Priority;
import org.blackbergx9.taskmanagementsystem.util.Status;

import java.time.LocalDateTime;

@Data
public class CreateTaskRequestDto {

    @NotBlank(message = "Invalid Title")
    @Size(min = 5, max = 100, message = "Size out of bound")
    private String title;

    @NotBlank(message = "Description Required")
    @Min(value = 10, message = "Size out of bound")
    private String description;

    @NotNull(message = "Assignee Name Required")
    @NotBlank(message = "Assignee Name id Invalid")
    @Size(min = 3, max = 55, message = "Size out of bound")
    private String assignee;

    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.MEDIUM;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @FutureOrPresent(message = "Due Date is already Expired")
    private LocalDateTime dueDate;

}
