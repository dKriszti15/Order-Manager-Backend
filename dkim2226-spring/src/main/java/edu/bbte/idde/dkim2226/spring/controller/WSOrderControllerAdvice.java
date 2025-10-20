package edu.bbte.idde.dkim2226.spring.controller;

import edu.bbte.idde.dkim2226.spring.dao.exceptions.RepositoryException;
import edu.bbte.idde.dkim2226.spring.service.exceptions.InvalidOrderException;
import edu.bbte.idde.dkim2226.spring.service.exceptions.OrderIdAlreadyExists;
import edu.bbte.idde.dkim2226.spring.service.exceptions.OrderIdNotFoundException;
import edu.bbte.idde.dkim2226.spring.service.exceptions.ProductIdNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Stream;

@ControllerAdvice
@ResponseBody
public class WSOrderControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final Stream<String> handleConstraintViolation(ConstraintViolationException e) {
        return e.getConstraintViolations().stream()
                .map(it -> it.getPropertyPath().toString() + ": " + it.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final Stream<String> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        return e.getBindingResult().getFieldErrors().stream()
                .map(it -> it.getField() + ": " + it.getDefaultMessage());
    }

    @ExceptionHandler(OrderIdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final String handleIdNotFound(OrderIdNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(OrderIdAlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public final String handleIdAlreadyExists(OrderIdAlreadyExists e) {
        return e.getMessage();
    }

    @ExceptionHandler(InvalidOrderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final String handleInvalidOrder(InvalidOrderException e) {
        return e.getMessage();
    }

    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final String handleRepositoryException(RepositoryException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ProductIdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final String handleProductIdNotFound(ProductIdNotFoundException e) {
        return e.getMessage();
    }

}
