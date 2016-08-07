package andrey019.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(CookieTheftException.class)
    public String handleAllException(Exception ex) {
        return "forward:/auth/login";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String notFound() {
        return "forward:/";
    }
}
