package no.kokab.myBlog.model.category;

import jakarta.persistence.Column;

public record CategoryRequest (
    @Column(nullable = false, unique = true)
    @jakarta.validation.constraints.NotBlank(message = "Name is required")
    @jakarta.validation.constraints.Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    String name,

    @Column(nullable = false)
    @jakarta.validation.constraints.NotBlank(message = "Description is required")
    @jakarta.validation.constraints.Size(min = 3, max = 255, message = "Description must be between 3 and 255 characters")
    String description
) {}