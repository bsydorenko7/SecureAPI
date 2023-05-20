package ua.knu.fit.sydorenko.secureapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {

    @JsonProperty("user_id")
    private Long userId;
    private String token;
    @JsonProperty("issued_time")
    private Date issuedTime;
    @JsonProperty("expires_time")
    private Date expiresTime;

}
