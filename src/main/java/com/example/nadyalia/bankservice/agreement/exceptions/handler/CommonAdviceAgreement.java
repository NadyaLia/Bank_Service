package com.example.nadyalia.bankservice.agreement.exceptions.handler;

import com.example.nadyalia.bankservice.agreement.exceptions.AgreementNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonAdviceAgreement {

    @ExceptionHandler(AgreementNotFoundException.class)
    public ResponseEntity<Response> handleException(AgreementNotFoundException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
