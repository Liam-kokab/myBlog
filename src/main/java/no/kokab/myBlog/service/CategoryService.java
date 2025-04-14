package no.kokab.myBlog.service;

import no.kokab.myBlog.exception.CategoryAlreadyExistException;
import no.kokab.myBlog.exception.ElementNotFoundException;
import no.kokab.myBlog.model.blog.CategoryEntity;
import no.kokab.myBlog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    public CategoryEntity getCategoryById(Long categoryId) {
        return categoryRepository.findByCategoryId(categoryId);
    }

    public CategoryEntity createCategory(CategoryEntity category) {
        Page<CategoryEntity> existingCategories = categoryRepository.findAllByName(category.getName(), null);

        if (existingCategories.hasContent()) {
            throw new CategoryAlreadyExistException("Category with this name already exists");
        }

        return categoryRepository.save(category);
    }

    public CategoryEntity updateCategory(Long categoryId, CategoryEntity category) {
        CategoryEntity existingCategory = categoryRepository.findByCategoryId(categoryId);
        if (existingCategory == null) {
            throw new ElementNotFoundException("Category not found");
        }

        Page<CategoryEntity> existingCategories = categoryRepository.findAllByName(category.getName(), null);
        if (existingCategories.hasContent()) {
            existingCategories.getContent()
                .forEach(existing -> {
                    if (!existing.getCategoryId().equals(categoryId)) {
                        throw new CategoryAlreadyExistException("Category with this name already exists");
                    }
                });
        }

        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());

        return categoryRepository.save(existingCategory);
    }

    public boolean deleteCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ElementNotFoundException("Category not found");
        }

        categoryRepository.deleteById(categoryId);
        return true;
    }
}
