package no.kokab.myBlog.controller;

import jakarta.validation.Valid;
import no.kokab.myBlog.exception.InvalidUserRoleException;
import no.kokab.myBlog.model.user.RoleName;
import no.kokab.myBlog.model.user.UserEntity;
import no.kokab.myBlog.model.user.UserWithToken;
import no.kokab.myBlog.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

@RestController
@RequestMapping("/sign-up")
@Validated
public class signUpController {

    private final UserService userService;

    public signUpController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserWithToken createUser(@Valid @RequestBody UserEntity user) {
        if (!Objects.equals(user.getRole(), RoleName.USER.toString()) && !Objects.equals(user.getRole(), RoleName.EDITOR.toString())) {
            throw new InvalidUserRoleException("Invalid role: " + user.getRole());
        }

        UserEntity newUser = userService.createUser(user);

        return new UserWithToken(newUser);
    }

}
