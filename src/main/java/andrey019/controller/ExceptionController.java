package andrey019.controller;

import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(CookieTheftException.class)
    public String handleAllException(Exception ex) {
        return "forward:/";
    }
}
