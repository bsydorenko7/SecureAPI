package ua.knu.fit.sydorenko.secureapi.dto;

import lombok.Data;

@Data
public class ErrorResponse {

    private int status;
    private String message;
}
