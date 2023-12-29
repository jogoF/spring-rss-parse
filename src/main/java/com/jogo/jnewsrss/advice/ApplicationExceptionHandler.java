package com.jogo.jnewsrss.advice;

import com.jogo.jnewsrss.exceptions.NotFoundException;
import com.jogo.jnewsrss.exceptions.ParsingErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@RestControllerAdvice
public class ApplicationExceptionHandler {


    @ExceptionHandler
    public ResponseEntity<AppResp> catchInvalidArgumentException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(new AppResp(HttpStatus.BAD_REQUEST.value(), "Invaild path, check docs!"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<AppResp> catchMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        return new ResponseEntity<>(new AppResp(HttpStatus.BAD_REQUEST.value(), "Invalid path, please check docs."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<AppResp> catchMethodArgumentTypeMismatch(NoResourceFoundException e) {
        return new ResponseEntity<>(new AppResp(HttpStatus.BAD_REQUEST.value(), "Invalid path, please check docs."), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity<AppResp> catchParsingErrorException(ParsingErrorException e) {
        return new ResponseEntity<>(new AppResp(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<AppResp> catchNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(new AppResp(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
