package co.com.neoris.minibank.app.exceptions;

public class ApiResponseException extends RuntimeException{

    public ApiResponseException(String s) {
        super(s);
    }

    public ApiResponseException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
