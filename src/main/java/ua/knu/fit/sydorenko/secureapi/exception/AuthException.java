package ua.knu.fit.sydorenko.secureapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AuthException extends ApiException {

    public AuthException(String message) {
        super(message, "AUTHENTICATION_FAILED");
    }
}
