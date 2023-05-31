package ua.knu.fit.sydorenko.secureapi.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ua.knu.fit.sydorenko.secureapi.dto.AuthRequestDto;
import ua.knu.fit.sydorenko.secureapi.dto.AuthResponseDto;
import ua.knu.fit.sydorenko.secureapi.dto.UserDto;
import ua.knu.fit.sydorenko.secureapi.entity.UserEntity;
import ua.knu.fit.sydorenko.secureapi.mapper.UserMapper;
import ua.knu.fit.sydorenko.secureapi.security.CustomPrincipal;
import ua.knu.fit.sydorenko.secureapi.security.SecurityService;
import ua.knu.fit.sydorenko.secureapi.service.UserService;
import ua.knu.fit.sydorenko.secureapi.validation.Validator;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthRestControllerV1 {

    private final SecurityService securityService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final Validator validator;

    @PostMapping("/register")
    public Mono<UserDto> register(@RequestBody UserDto userDto) {
        UserEntity userEntity = userMapper.map(userDto);
        return userService.registerUser(userEntity)
                .map(userMapper::map);
    }

    @PostMapping("/login")
    public Mono<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto) {
        return securityService.authenticate(validator.validateValue(authRequestDto.getUsername()), authRequestDto.getPassword())
                .flatMap(tokenDetails -> Mono.just(
                        AuthResponseDto.builder()
                                .userId(tokenDetails.getUserId())
                                .token(tokenDetails.getToken())
                                .issuedTime(tokenDetails.getIssuedTime())
                                .expiresTime(tokenDetails.getExpiresTime())
                                .build()
                ));
    }

    @GetMapping("/info")
    @PreAuthorize("hasRole('ADMIN_ROLE')")
    public Mono<UserDto> getUserInfo(Authentication authentication) {
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();

        return userService.getUserById(customPrincipal.getId())
                .map(userMapper::map);
    }

    @DeleteMapping("/delete/{userId}")
    public Mono<Void> changeUserStatus(@PathVariable(value="userId") Long userId) {
        return userService.deleteUser(userId);
    }

}
