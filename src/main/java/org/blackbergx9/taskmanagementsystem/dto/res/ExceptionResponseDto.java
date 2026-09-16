package org.blackbergx9.taskmanagementsystem.dto.res;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.Instant;

@JsonPropertyOrder({

        "error",
        "status",
        "message",
        "timestamp",
        "path"
})

@Data
public class ExceptionResponseDto {

    private Instant timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

}
