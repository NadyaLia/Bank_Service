package com.example.nadyalia.bankservice.client.exceptions.handler;

import com.example.nadyalia.bankservice.client.exceptions.ClientNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class CommonAdviceClient {

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<Response> handleException(ClientNotFoundException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
