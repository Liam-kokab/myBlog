package no.kokab.myBlog.controller;

import no.kokab.myBlog.model.post.CategoryEntity;

import no.kokab.myBlog.service.CategoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Validated
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryEntity> getCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{categoryId}")
    public CategoryEntity getCategoryById(@PathVariable Long categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @PostMapping
    public CategoryEntity createCategory(@RequestBody CategoryEntity category) {
        return categoryService.createCategory(category);
    }

    @PutMapping("/{categoryId}")
    public CategoryEntity updateCategory(@PathVariable Long categoryId, @RequestBody CategoryEntity category) {
        return categoryService.updateCategory(categoryId, category);
    }

    @DeleteMapping("/{categoryId}")
    public String deleteCategory(@PathVariable Long categoryId) {
        return categoryService.deleteCategory(categoryId)
            ? String.format("category with id %d deleted", categoryId)
            : String.format("category with id %d not found", categoryId);
    }

}
