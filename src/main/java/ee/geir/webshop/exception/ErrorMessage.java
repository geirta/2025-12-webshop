package ee.geir.webshop.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

//@Getter
//@Setter
//@Data = Getter & Setter
@Data
public class ErrorMessage {
    private String message;
    private int status;
    private Date date;
}
