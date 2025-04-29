package no.kokab.myBlog.model.user;

import jakarta.validation.constraints.*;

public record SignUpRequestUser(
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    String name,

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    String email,

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 6 characters long")
    String password,

    @NotNull(message = "Role is required")
    @Pattern(
        regexp = "ADMIN|EDITOR|USER",
        message = "Invalid role"
    )
    String role
) { }
