package no.kokab.myBlog.model.blog;

import jakarta.persistence.*;
import no.kokab.myBlog.converter.ContentConverter;
import no.kokab.myBlog.model.content.Content;
import org.springframework.data.annotation.Id;

import java.util.List;

@Entity
@Table(name = "post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @jakarta.persistence.Id
    private Long postId;

    private String title;

    @Lob
    @Convert(converter = ContentConverter.class)
    @Column(columnDefinition = "jsonb")
    private List<Content> content;

    private Long authorId;
    private String createdAt;
    private String updatedAt;

    public PostEntity() {
    }

    public PostEntity(Long postId, String title, List<Content> content, Long authorId, String createdAt, String updatedAt) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
