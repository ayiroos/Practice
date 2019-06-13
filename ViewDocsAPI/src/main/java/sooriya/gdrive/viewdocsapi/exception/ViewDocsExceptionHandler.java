package sooriya.gdrive.viewdocsapi.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ViewDocsExceptionHandler {


    @ExceptionHandler(ViewDocsException.class)
    public ResponseEntity<ErrorMessage> handleException(ViewDocsException exception){

        ErrorMessage em = new ErrorMessage();

        em.setStatus(exception.getStatus());
        em.setMessage(exception.getMessage());

        return new ResponseEntity(em, em.getStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> handleRuntimeException(RuntimeException re) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage("Internal Server Error");
        re.printStackTrace();
        return new ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
