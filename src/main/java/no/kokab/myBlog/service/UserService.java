package no.kokab.myBlog.service;

import no.kokab.myBlog.exception.UserAlreadyExistsException;
import no.kokab.myBlog.model.user.LoginRequest;
import no.kokab.myBlog.model.user.SignUpRequestUser;
import no.kokab.myBlog.model.user.UserEntity;
import no.kokab.myBlog.model.user.UserPublic;
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

    public UserEntity createUser(SignUpRequestUser user) {
        // Check if the user already exists
        if (userRepository.existsByEmail(user.email())) {
            throw new UserAlreadyExistsException("Email already exists" + user.email());
        }

        final String passwordHash = PasswordUtil.hashPassword(user.password(), user.email());

        return userRepository.save(
            new UserEntity(
                null,
                user.name(),
                user.email(),
                passwordHash,
                user.role(),
                null,
                null
            )
        );
    }

    public UserPublic getUserPublicProfileById(Long userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user == null) return null;

        return new UserPublic(
            user.getUserId(),
            user.getName()
        );
    }

    public Optional<UserEntity> login(LoginRequest loginRequest) {
        UserEntity user = userRepository.findByEmail(loginRequest.email());

        if (user == null) return Optional.empty();

        if (!PasswordUtil.verifyPassword(loginRequest.password(), user.getEmail(), user.getPassword())) {
            return Optional.empty();
        }

        return Optional.of(user);
    }

    public boolean isAdmin(Long userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        return user != null && user.getRole().equals("ADMIN");
    }

}