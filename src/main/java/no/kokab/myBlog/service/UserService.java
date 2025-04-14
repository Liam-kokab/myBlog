package no.kokab.myBlog.service;

import no.kokab.myBlog.exception.UserAlreadyExistsException;
import no.kokab.myBlog.model.user.LoginRequest;
import no.kokab.myBlog.model.user.UserEntity;
import no.kokab.myBlog.repository.UserRepository;
import no.kokab.myBlog.util.PasswordUtil;

import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(UserEntity user) {
        // Check if the user already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists" + user.getEmail());
        }

        final String passwordHash = PasswordUtil.hashPassword(user.getPassword(), user.getEmail());

        user.setPassword(passwordHash);
        return userRepository.save(user);
    }

    public Optional<UserEntity> login(LoginRequest loginRequest) {
        UserEntity user = userRepository.findByEmail(loginRequest.getEmail());

        if (user == null) return Optional.empty();

        if (!PasswordUtil.verifyPassword(loginRequest.getPassword(), user.getEmail(), user.getPassword())) {
            return Optional.empty();
        }

        return Optional.of(user);
    }

}