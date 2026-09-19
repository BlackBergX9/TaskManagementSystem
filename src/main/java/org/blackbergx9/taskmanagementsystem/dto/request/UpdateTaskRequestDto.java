package org.blackbergx9.taskmanagementsystem.dto.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.blackbergx9.taskmanagementsystem.util.Priority;
import org.blackbergx9.taskmanagementsystem.util.Status;

import java.time.LocalDateTime;

@Data
public class UpdateTaskRequestDto {

    @Size(min = 5, max = 100, message = "Size out of bound")
    private String title;

    @Size(min = 10, message = "Size out of bound")
    private String description;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Status status;

    @FutureOrPresent(message = "Due Date is already Expired")
    private LocalDateTime dueDate;

}
