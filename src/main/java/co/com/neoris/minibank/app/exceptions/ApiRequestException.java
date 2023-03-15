package co.com.neoris.minibank.app.exceptions;

public class ApiRequestException extends RuntimeException{

    public ApiRequestException(String s) {
        super(s);
    }

    public ApiRequestException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
