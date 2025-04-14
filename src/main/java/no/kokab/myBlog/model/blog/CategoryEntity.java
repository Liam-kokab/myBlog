package no.kokab.myBlog.model.blog;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

@Entity
@Table(name = "category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @jakarta.persistence.Id
    private Long categoryId;

    @Column(nullable = false, unique = true)
    @jakarta.validation.constraints.NotBlank(message = "Name is required")
    @jakarta.validation.constraints.Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @Column(nullable = false)
    @jakarta.validation.constraints.NotBlank(message = "Description is required")
    @jakarta.validation.constraints.Size(min = 3, max = 255, message = "Description must be between 3 and 255 characters")
    private String description;

    public CategoryEntity() {
    }

    public CategoryEntity(Long categoryId, String name, String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
    }

    public Long getCategoryId() {
            return categoryId;
        }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
