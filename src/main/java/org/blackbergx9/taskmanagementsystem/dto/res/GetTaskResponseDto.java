package org.blackbergx9.taskmanagementsystem.dto.res;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.blackbergx9.taskmanagementsystem.util.Priority;
import org.blackbergx9.taskmanagementsystem.util.Status;

import java.time.Instant;
import java.time.LocalDateTime;

@JsonPropertyOrder({
    "id",           //
    "assignee",     //
    "title",        //
    "description",  //
    "priority",     //
    "status",       //
    "dueDate",      //
    "createdAt",    //
    "updatedAt",    //
})

@Data
public class GetTaskResponseDto {

    private Long id;

    private String title;
    private String description;
    private String assignee;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime dueDate;

    private Instant createdAt;
    private Instant updatedAt;
}
