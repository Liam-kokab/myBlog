package no.kokab.myBlog.controller;

import no.kokab.myBlog.exception.WrongEmailOrPassword;
import no.kokab.myBlog.model.user.LoginRequest;
import no.kokab.myBlog.model.user.UserEntity;
import no.kokab.myBlog.model.user.UserWithToken;
import no.kokab.myBlog.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/login")
public class LogInController {

    private final UserService userService;

    public LogInController(UserService userService) {
        this.userService = userService;
    }

    // login using email and password
    @PostMapping
    public UserWithToken login(@RequestBody LoginRequest loginRequest) {
        Optional<UserEntity> userRes = userService.login(loginRequest);

        if (userRes.isEmpty()) {
            throw new WrongEmailOrPassword("Wrong email or password");
        } else {
            return new UserWithToken(userRes.get());
        }
    }

}