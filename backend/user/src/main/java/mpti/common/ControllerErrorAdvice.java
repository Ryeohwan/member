package mpti.common;

import mpti.common.errors.EmailDuplicateException;
import mpti.common.errors.TokenNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerErrorAdvice {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(TokenNotFoundException.class)
    public ErrorResponse handleTokenNotFoundException(Exception e){
        return new ErrorResponse(e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EmailDuplicateException.class)
    public ErrorResponse handleEmailDuplicateException(Exception e){
        return new ErrorResponse(e.getMessage());
    }

}