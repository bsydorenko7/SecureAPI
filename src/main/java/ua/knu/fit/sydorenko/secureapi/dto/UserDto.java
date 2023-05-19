package ua.knu.fit.sydorenko.secureapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ua.knu.fit.sydorenko.secureapi.entity.UserRole;

import java.time.LocalDateTime;

@Data
public class UserDto {

    private Long id;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private UserRole role;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private boolean enabled;
    @JsonProperty("created_time")
    private LocalDateTime createdTime;
    @JsonProperty("updated_time")
    private LocalDateTime updatedTime;
}
