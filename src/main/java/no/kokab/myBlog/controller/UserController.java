package no.kokab.myBlog.controller;

import jakarta.validation.Valid;
import no.kokab.myBlog.model.user.SignUpRequestUser;
import no.kokab.myBlog.model.user.UserPublic;
import no.kokab.myBlog.service.UserService;
import org.springframework.web.bind.annotation.*;

/*
 * TODO: UserController is mostly a placeholder for now.
 *  later it will be used to handle user-related operations by admin.
 */

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserPublic createUser(@Valid @RequestBody SignUpRequestUser user) {
        return UserPublic.fromUserEntity(userService.createUser(user));
    }

}