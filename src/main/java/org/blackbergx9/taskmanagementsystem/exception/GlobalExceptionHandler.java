package org.blackbergx9.taskmanagementsystem.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.blackbergx9.taskmanagementsystem.dto.res.ExceptionResponseDto;
import org.blackbergx9.taskmanagementsystem.dto.res.ValidationExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDto> handleException(Exception ex,  HttpServletRequest request)
    {
        // TODO: Message wrap needed.

        ExceptionResponseDto resBody = new ExceptionResponseDto();

        resBody.setTimestamp(Instant.now());
        resBody.setStatus(HttpStatus.NOT_FOUND.value());
//        resBody.setMessage("EX: "+ex.toString());
        resBody.setMessage("EX: "+ex.getMessage());
        resBody.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        resBody.setPath(getUri(request));

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(resBody);

    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponseDto> handleRuntimeException(RuntimeException ex,  HttpServletRequest request)
    {
        ExceptionResponseDto resBody = new ExceptionResponseDto();

        // TODO: Message wrap needed.

        resBody.setTimestamp(Instant.now());
        resBody.setStatus(HttpStatus.NOT_FOUND.value());
//        resBody.setMessage("REX: "+ex.toString());
        resBody.setMessage("REX: "+ex.getMessage());
        resBody.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        resBody.setPath(getUri(request));

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(resBody);

    }

    @ExceptionHandler(DatabaseErrorException.class)
    public ResponseEntity<ExceptionResponseDto> handleDatabaseErrorException(DatabaseErrorException ex, HttpServletRequest request)
    {
        ExceptionResponseDto resBody = new ExceptionResponseDto();

        resBody.setTimestamp(Instant.now());
        resBody.setStatus(HttpStatus.NOT_FOUND.value());
        resBody.setMessage(ex.getMessage());
        resBody.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        resBody.setPath(getUri(request));

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(resBody);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request)
    {
        ExceptionResponseDto resBody = new ExceptionResponseDto();


        resBody.setTimestamp(Instant.now());
        resBody.setStatus(HttpStatus.NOT_FOUND.value());
        resBody.setMessage(ex.getMessage());
        resBody.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        resBody.setPath(getUri(request));

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(resBody);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionResponseDto> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, HttpServletRequest request)
    {

        ValidationExceptionResponseDto resBody = new ValidationExceptionResponseDto();
        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach( error -> fieldErrors
                        .put( error.getField(), error.getDefaultMessage() )
                );

        resBody.setFieldErrors(fieldErrors);
        resBody.setTimestamp(Instant.now());
        resBody.setStatus(HttpStatus.BAD_REQUEST.value());
        resBody.setMessage("Validation Failed!");
        resBody.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        resBody.setPath(getUri(request));


        return  ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(resBody);
    }


    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ValidationExceptionResponseDto> handleHandlerMethodValidationException(
            HandlerMethodValidationException ex, HttpServletRequest request
    ) {

        ValidationExceptionResponseDto resBody = new ValidationExceptionResponseDto();
        Map<String, String> fieldErrors = new HashMap<>();

        ex.getAllErrors()
                .forEach( error -> {

                    String fieldName = "msg";

                    if (error instanceof FieldError)
                        fieldName = ( (FieldError) error ).getField();  // Gets the Field Name.

                    fieldErrors
                            .put( fieldName, error.getDefaultMessage() );   // Gets Validation Error message.
                });

        resBody.setFieldErrors(fieldErrors);
        resBody.setTimestamp(Instant.now());
        resBody.setStatus(HttpStatus.BAD_REQUEST.value());
        resBody.setMessage("Validation Failed!");
        resBody.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        resBody.setPath(getUri(request));


        return  ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(resBody);
    }


    private String getUri(HttpServletRequest request)
    {
        String requestUri = request.getRequestURI();
        String queryString = request.getQueryString();

        return (queryString != null) ? (requestUri + "?" + queryString) : requestUri;
    }
}
