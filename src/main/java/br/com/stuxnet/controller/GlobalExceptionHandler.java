package br.com.stuxnet.controller;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Erros de Bean Validation (@NotBlank, @Min, @Pattern nos DTOs) -> 400 Bad Request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationErrors(MethodArgumentNotValidException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Um ou mais campos enviados possuem formato inválido."
        );
        problem.setTitle("Erro de Validação de Dados");

        Map<String, String> camposInvalidos = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> error.getDefaultMessage() != null ? error.getDefaultMessage() : "Inválido",
                        (msg1, msg2) -> msg1
                ));

        problem.setProperty("erros", camposInvalidos);
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }

    // 2. Regras de Negócio (SKU duplicado, Estoque insuficiente) -> 422 Unprocessable Entity
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ProblemDetail handleBusinessException(RuntimeException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNPROCESSABLE_ENTITY,
                ex.getMessage()
        );
        problem.setTitle("Violação de Regra de Negócio");
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }

    // 3. Recursos não encontrados no banco -> 404 Not Found
    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleNotFound(EntityNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
        problem.setTitle("Recurso Não Encontrado");
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }
}