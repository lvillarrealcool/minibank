package co.com.neoris.minibank.app.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
public class ApiException {

    private final String mensaje;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;

    public ApiException(String mensaje, HttpStatus httpStatus, ZonedDateTime timestamp) {
        this.mensaje = mensaje;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }
}
