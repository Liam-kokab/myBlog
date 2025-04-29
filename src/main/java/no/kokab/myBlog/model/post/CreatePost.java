package no.kokab.myBlog.model.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import no.kokab.myBlog.model.post.content.Content;

import java.util.List;

public record CreatePost(
        @NotBlank(message = "Title is required")
        @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
        String title,

        @NotNull(message = "content is required")
        List<Content> content,

        @NotNull(message = "a category is required")
        long categoryId,

        @NotNull(message = "Tags are required")
        List<String> tags
) {}
