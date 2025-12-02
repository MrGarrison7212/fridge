package com.bojan.fridge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FridgeItemNotFoundException.class)
    public ResponseEntity<ApiError> handleItemNotFound(FridgeItemNotFoundException ex){
        ApiError error = ApiError.of("ITEM NOT FOUND", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(FridgeException.class)
    public ResponseEntity<ApiError> handleFridgeException(FridgeException ex){
        ApiError error = ApiError.of("FRIDGE_ERROR", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        String message = "Validation failed for request body";
        ApiError error = ApiError.of("VALIDATION ERROR", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleOther(Exception ex) {
        ApiError error = ApiError.of("INTERNAL ERROR", "Unexpected server error");
        System.out.println("EX TYPE BREEE = " + ex.getClass().getName());
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
