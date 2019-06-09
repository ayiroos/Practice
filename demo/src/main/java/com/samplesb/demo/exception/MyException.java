package com.samplesb.demo.exception;

import org.springframework.http.HttpStatus;

public class MyException extends RuntimeException {


    private HttpStatus status;
    private String message;
    private Throwable t;

    private String fuk;

    public MyException() {

    }

    public MyException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public MyException(HttpStatus status, String message, Throwable t) {
        this.status = status;
        this.message = message;
        this.t = t;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getT() {
        return t;
    }

    public void setT(Throwable t) {
        this.t = t;
    }
}
