package ua.knu.fit.sydorenko.secureapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ua.knu.fit.sydorenko.secureapi.entity.UserEntity;
import ua.knu.fit.sydorenko.secureapi.entity.UserRole;
import ua.knu.fit.sydorenko.secureapi.repository.UserRepository;
import ua.knu.fit.sydorenko.secureapi.validation.Validator;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;

    public Mono<UserEntity> registerUser(UserEntity inputUser) {

        return userRepository.save(
                new UserEntity().toBuilder()
                        .username(validator.validateValue(inputUser.getUsername()))
                        .firstName(validator.validateValue(inputUser.getUsername()))
                        .lastName(validator.validateValue(inputUser.getLastName()))
                        .password(passwordEncoder.encode(inputUser.getPassword()))
                        .role(UserRole.ROLE_USER)
                        .enabled(true)
                        .createdTime(LocalDateTime.now())
                        .updatedTime(LocalDateTime.now())
                        .build()
        ).doOnSuccess(u -> {
            log.info("IN registerUser - user: {} created", u);
        });
    }

    public Mono<UserEntity> registerAdmin(UserEntity inputUser) {

        return userRepository.save(
                new UserEntity().toBuilder()
                        .username(validator.validateValue(inputUser.getUsername()))
                        .firstName(validator.validateValue(inputUser.getUsername()))
                        .lastName(validator.validateValue(inputUser.getLastName()))
                        .password(passwordEncoder.encode(inputUser.getPassword()))
                        .role(UserRole.ROLE_ADMIN)
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

    public Mono<Void> deleteUser(long id) {
        return userRepository.deleteById(id).doOnSuccess(u -> {
            log.info("IN deleteUser - user with id: {} deleted", id);
        });
    }
}
