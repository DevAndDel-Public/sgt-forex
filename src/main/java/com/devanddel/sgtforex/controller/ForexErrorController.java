package com.devanddel.sgtforex.controller;

import com.devanddel.sgtforex.domain.ForexResult;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static java.util.Collections.emptySet;

/**
 * Date: 14/6/21.
 * Author: Dev&Del
 */
@RestControllerAdvice
public class ForexErrorController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ForexResult result = ForexResult.builder()
                .type("/errors/request-not-found")
                .title(ex.getClass().getSimpleName())
                .status(HttpStatus.NOT_FOUND.value())
                .detail(ex.getMessage())
                .instance(getURIDescription(request))
                .result(emptySet())
                .build();
        return ResponseEntity.status(status).body(result);
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class, IllegalArgumentException.class})
    protected ResponseEntity<Object> handleArgumentTypeMismatch(RuntimeException ex, WebRequest request) {
        ForexResult result = ForexResult.builder()
                .type("/errors/request-error")
                .title(ex.getClass().getSimpleName())
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(ex.getMessage())
                .instance(getURIDescription(request))
                .result(emptySet())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ForexResult result = ForexResult.builder()
                .type("/errors/internal-server-error")
                .title(ex.getClass().getSimpleName())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail(ex.getMessage())
                .instance(getURIDescription(request))
                .result(emptySet())
                .build();
        return ResponseEntity.status(status).body(result);
    }

    private static String getURIDescription(WebRequest request){
        if (request instanceof ServletWebRequest) {
            return ((ServletWebRequest) request).getRequest().getRequestURI();
        } else {
            return request.getDescription(false);
        }
    }

}
