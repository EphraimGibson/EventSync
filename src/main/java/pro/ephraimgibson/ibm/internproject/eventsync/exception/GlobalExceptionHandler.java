package pro.ephraimgibson.ibm.internproject.eventsync.exception;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.NoArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@NoArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDatabaseError(DataAccessException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Database connection failed");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeError(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Something went wrong");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleInvalidInput(ConstraintViolationException e){
        String customMessage = e.getConstraintViolations()
                .stream()
                .findFirst()
                .map(ConstraintViolation::getMessage)
                .orElse("Validation error: You are missing a require field");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customMessage);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEventNotFound(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleInvalidInput(MethodArgumentNotValidException e){
       String customMessage = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .findFirst()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .orElse("Validation error: You are missing a required field");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customMessage);
    }


}

