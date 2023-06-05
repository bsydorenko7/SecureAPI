package ua.knu.fit.sydorenko.secureapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ua.knu.fit.sydorenko.secureapi.entity.UserRole;

import java.time.LocalDateTime;

@Data
public class UserDto {

    private Long id;

    @NotBlank(message = "Username name cannot be empty")
    private String username;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private UserRole role;

    @Size(message = "First name must be between 2 and 25 characters", min = 2, max = 25)
    @JsonProperty("first_name")
    private String firstName;

    @Size(message = "Last name must be between 2 and 25 characters", min = 2, max = 25)
    @JsonProperty("last_name")
    private String lastName;

    private boolean enabled;

    @JsonProperty("created_time")
    private LocalDateTime createdTime;

    @JsonProperty("updated_time")
    private LocalDateTime updatedTime;
}
