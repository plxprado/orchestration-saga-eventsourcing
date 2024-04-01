//package com.prado.tools.toolkitdev.eventsourcing.rest;
//
//import akka.pattern.StatusReply;
//import com.prado.tools.toolkitdev.eventsourcing.rest.dto.ErrorMessage;
//import org.hibernate.exception.ConstraintViolationException;
//import org.springframework.dao.DuplicateKeyException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.WebRequest;
//
//@RestControllerAdvice
//public class ErrorHandler {
//
//    @ExceptionHandler(value
//            = { IllegalArgumentException.class, IllegalStateException.class })
//    public ResponseEntity<ErrorMessage> handleConflict(
//            RuntimeException ex, WebRequest request) {
//        String bodyOfResponse = "This should be application specific";
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage(bodyOfResponse));
//    }
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(value = { ConstraintViolationException.class})
//    public ErrorMessage constraintViolationException(
//            DuplicateKeyException ex, WebRequest request) {
//        return new ErrorMessage(ex.getMessage());
//    }
//}
