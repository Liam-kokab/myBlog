package no.kokab.myBlog.repository;

import no.kokab.myBlog.model.post.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @NonNull
    Page<CategoryEntity> findAll(@NonNull Pageable pageable);

    @Nullable
    CategoryEntity findByCategoryId(long categoryId);

    @NonNull
    Page<CategoryEntity> findAllByName(@NonNull String categoryName, Pageable pageable);

}
