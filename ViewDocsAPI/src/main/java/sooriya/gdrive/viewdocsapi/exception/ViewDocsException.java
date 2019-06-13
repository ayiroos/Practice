package sooriya.gdrive.viewdocsapi.exception;


import org.springframework.http.HttpStatus;

public class ViewDocsException extends RuntimeException {

    private HttpStatus status;
    private String message;
    private Throwable t;

    public ViewDocsException() {
    }

    public ViewDocsException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ViewDocsException(HttpStatus status, String message, Throwable t) {
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
