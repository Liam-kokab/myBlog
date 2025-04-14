package no.kokab.myBlog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UserCreationException extends RuntimeException {
    public UserCreationException(String message) {
        super(message);
    }
}