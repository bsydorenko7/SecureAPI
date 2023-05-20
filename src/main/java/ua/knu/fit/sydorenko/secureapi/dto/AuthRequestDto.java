package ua.knu.fit.sydorenko.secureapi.dto;

import lombok.Data;

@Data
public class AuthRequestDto {

    private String username;
    private String password;
}
