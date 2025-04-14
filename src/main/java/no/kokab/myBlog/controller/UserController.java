package no.kokab.myBlog.controller;

import no.kokab.myBlog.model.user.UserEntity;
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
    public UserEntity createUser(@RequestBody UserEntity user) {
        return userService.createUser(user);
    }

}