package ee.geir.webshop.exception;

import ee.geir.webshop.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;


// see l2heb automaatselt k6ikidele controlleritele
@ControllerAdvice
public class ControllerAdviceHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleException(RuntimeException ex) {
        ErrorMessage error = new ErrorMessage();
        error.setMessage(ex.getMessage());
        error.setStatus(400);
        error.setDate(new Date());
        return ResponseEntity.status(400).body(error);
    }
}
