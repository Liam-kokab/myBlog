package no.kokab.myBlog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class WrongEmailOrPassword extends RuntimeException {
    public WrongEmailOrPassword(String message) {
        super(message);
    }
}
