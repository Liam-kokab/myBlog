package no.kokab.myBlog.service;

import no.kokab.myBlog.exception.CategoryAlreadyExistException;
import no.kokab.myBlog.exception.ElementNotFoundException;
import no.kokab.myBlog.model.category.CategoryEntity;
import no.kokab.myBlog.model.category.CategoryRequest;
import no.kokab.myBlog.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    public CategoryEntity getCategoryById(Long categoryId) {
        return categoryRepository.findByCategoryId(categoryId);
    }

    public CategoryEntity createCategory(CategoryRequest category) {
        Page<CategoryEntity> existingCategories = categoryRepository.findAllByName(category.name(), null);

        if (existingCategories.hasContent()) {
            throw new CategoryAlreadyExistException("Category with this name already exists");
        }

        return categoryRepository.save(new CategoryEntity(
            null,
            category.name(),
            category.description()
        ));
    }

    public CategoryEntity updateCategory(Long categoryId, CategoryRequest category) {
        CategoryEntity existingCategory = categoryRepository.findByCategoryId(categoryId);
        if (existingCategory == null) {
            throw new ElementNotFoundException("Category not found");
        }

        Page<CategoryEntity> existingCategories = categoryRepository.findAllByName(category.name(), null);
        if (existingCategories.hasContent()) {
            existingCategories.getContent()
                .forEach(existing -> {
                    if (!existing.getCategoryId().equals(categoryId)) {
                        throw new CategoryAlreadyExistException("Category with this name already exists");
                    }
                });
        }

        return categoryRepository.save(new CategoryEntity(
            existingCategory.getCategoryId(),
            category.name(),
            category.description()
        ));
    }

    public boolean deleteCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ElementNotFoundException("Category not found");
        }

        categoryRepository.deleteById(categoryId);
        return true;
    }
}
