package org.blackbergx9.taskmanagementsystem.dto.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.blackbergx9.taskmanagementsystem.util.Priority;
import org.blackbergx9.taskmanagementsystem.util.Status;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
public class UpdateTaskRequestDto {

//    @NotBlank(message = "Invalid Title")
    @Size(min = 3, max = 55, message = "Size out of bound")
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Status status;

    @FutureOrPresent(message = "Due Date is already Expired")
    private LocalDateTime dueDate;

}
