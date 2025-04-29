package no.kokab.myBlog.model.post;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import no.kokab.myBlog.converter.ContentConverter;
import no.kokab.myBlog.converter.StringListConverter;
import no.kokab.myBlog.model.post.content.Content;
import org.hibernate.annotations.ColumnTransformer;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Entity
@Table(name = "post")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
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

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now(ZoneOffset.UTC);
    }
}