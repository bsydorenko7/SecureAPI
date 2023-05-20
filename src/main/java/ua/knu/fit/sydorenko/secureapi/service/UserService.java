package ua.knu.fit.sydorenko.secureapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ua.knu.fit.sydorenko.secureapi.entity.UserEntity;
import ua.knu.fit.sydorenko.secureapi.entity.UserRole;
import ua.knu.fit.sydorenko.secureapi.repository.UserRepository;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<UserEntity> registerUser(UserEntity user) {
        return userRepository.save(
                user.toBuilder()
                        .password(passwordEncoder.encode(user.getPassword()))
                        .role(UserRole.USER_ROLE)
                        .enabled(true)
                        .createdTime(LocalDateTime.now())
                        .updatedTime(LocalDateTime.now())
                        .build()
        ).doOnSuccess(u -> {
            log.info("IN registerUser - user: {} created", u);
        });
    }

    public Mono<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Mono<UserEntity> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
