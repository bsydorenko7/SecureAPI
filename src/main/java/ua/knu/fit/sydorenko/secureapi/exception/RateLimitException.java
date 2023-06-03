package ua.knu.fit.sydorenko.secureapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.TOO_MANY_REQUESTS)
public class RateLimitException  extends ApiException {

    public RateLimitException(String message) {
        super(message, "RATE_LIMIT_EXCEPTION");
    }
}
