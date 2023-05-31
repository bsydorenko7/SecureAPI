package ua.knu.fit.sydorenko.secureapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.knu.fit.sydorenko.secureapi.dto.ErrorResponse;
import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(XSSServletException.class)
    public ErrorResponse handleSQLException(HttpServletRequest request,
                                            Exception ex){
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatus(HttpStatus.FORBIDDEN.value());
        errorResponse.setMessage(ex.getMessage());

        return errorResponse;
    }
}
