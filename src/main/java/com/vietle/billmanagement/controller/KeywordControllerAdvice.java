package com.vietle.billmanagement.controller;

import com.vietle.billmanagement.exception.BillManagementException;
import com.vietle.billmanagement.model.GenericResponse;
import com.vietle.billmanagement.model.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class KeywordControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BillManagementException.class)
    public ResponseEntity<GenericResponse> handleRTVCustomException(BillManagementException ex, WebRequest request) {
        log.error(String.format("BillManagementException occurs: %s", ex.getTransactionStatus().getDetailMessage()));
        int statusCd = ex.getTransactionStatus().getStatusCd();
        Status status = ex.getTransactionStatus();
        GenericResponse response = GenericResponse.builder().status(status).build();
        return new ResponseEntity<>(response, HttpStatus.resolve(statusCd));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse> handleGenericException(Exception ex, WebRequest request) {
        log.error(String.format("Exception occurs: %s", ex.getMessage()));
        String transactionId = request.getHeader("transactionId");
        String timestamp = request.getHeader("timestamp");
        Status status = Status.builder().statusCd(500).message(ex.getMessage()).timestamp(timestamp)
                .isSuccess(false).transactionId(transactionId).build();
        GenericResponse response = GenericResponse.builder().status(status).build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
