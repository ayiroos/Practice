package com.samplesb.demo.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DemoExceptionHandler {

    @ExceptionHandler(MyException.class)
    public ResponseEntity<ErrorMessage> handleMyException(MyException myException){

        ErrorMessage em = new ErrorMessage();

        em.setStatus(myException.getStatus());
        em.setMessage(myException.getMessage());

        System.out.println(myException.getT());

        return new ResponseEntity(em, em.getStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> handleRuntimeException(RuntimeException re) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(re.getMessage());
        return new ResponseEntity(errorMessage, HttpStatus.METHOD_NOT_ALLOWED);
    }






}
