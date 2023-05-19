package ua.knu.fit.sydorenko.secureapi.exception;

public class AuthException extends ApiException {

    public AuthException(String message) {
        super(message, "AUTHENTICATION_FAILED");
    }
}
