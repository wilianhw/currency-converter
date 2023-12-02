package dev.wilian.currencyconverter.api.exception;

import dev.wilian.currencyconverter.domain.exception.BusinessException;
import dev.wilian.currencyconverter.domain.exception.TransactionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Problem> handleBusinessException(BusinessException ex) {
        Problem problem = Problem.builder()
                .title("Business exception")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(ex.getMessage())
                .build();

        return ResponseEntity.badRequest().body(problem);
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<Problem> handleTransactionNotFoundException(TransactionNotFoundException ex) {
        Problem problem = Problem.builder()
                .title("Entity not found")
                .status(HttpStatus.NOT_FOUND.value())
                .detail(ex.getMessage())
                .build();

        return new ResponseEntity<>(problem, HttpStatus.NOT_FOUND);
    }
}
