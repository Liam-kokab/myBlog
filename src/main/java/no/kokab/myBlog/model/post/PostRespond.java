package no.kokab.myBlog.model.post;


import no.kokab.myBlog.model.category.CategoryEntity;
import no.kokab.myBlog.model.post.content.Content;
import no.kokab.myBlog.model.user.UserPublic;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public record PostRespond(
        Long postId,
        String title,
        List<Content> content,
        CategoryEntity category,
        UserPublic author,
        List<String> tags,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
