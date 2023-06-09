package ua.knu.fit.sydorenko.secureapi.controller.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/v1/user")
public class UserRestControllerV1 {

    private final SecurityService securityService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final Validator validator;

    @PostMapping("/register")
    public Mono<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {
        UserEntity userEntity = userMapper.map(userDto);
        return userService.registerUser(userEntity)
                .map(userMapper::map);
    }

    @PostMapping("/admin/register")
    public Mono<UserDto> registerAdmin(@Valid @RequestBody UserDto userDto) {
        UserEntity userEntity = userMapper.map(userDto);
        return userService.registerAdmin(userEntity)
                .map(userMapper::map);
    }

    @PostMapping("/login")
    public Mono<AuthResponseDto> login(@Valid @RequestBody AuthRequestDto authRequestDto) {
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
