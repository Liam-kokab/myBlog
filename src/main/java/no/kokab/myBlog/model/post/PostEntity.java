package no.kokab.myBlog.model.post;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import no.kokab.myBlog.converter.ContentConverter;
import no.kokab.myBlog.converter.StringListConverter;
import no.kokab.myBlog.model.content.Content;
import org.hibernate.annotations.ColumnTransformer;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Entity
@Table(name = "post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @jakarta.persistence.Id
    private Long postId;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @Convert(converter = ContentConverter.class)
    @Column(columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    private List<Content> content;

    @NotNull(message = "a category is required")
    private long categoryId;

    @NotNull(message = "an author is required")
    private Long userId;

    @Convert(converter = StringListConverter.class)
    @NotNull(message = "Tags are required")
    @Column(nullable = false, columnDefinition = "TEXT[]")
    private List<String> tags;

    private LocalDateTime createdAt = LocalDateTime.now(ZoneOffset.UTC);
    private LocalDateTime updatedAt = LocalDateTime.now(ZoneOffset.UTC);

    public PostEntity() {
    }

    // Getters and Setters
    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
