package org.blackbergx9.taskmanagementsystem.dto.res;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.Instant;
import java.util.Map;

@JsonPropertyOrder(
    {
            "error",
            "status",
            "message",
            "fieldErrors",
            "timestamp",
            "path"
    }
)

@Data
public class ValidationExceptionResponseDto {

    private Instant timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private Map<String, String> fieldErrors;

}
