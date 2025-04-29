package no.kokab.myBlog.repository;

import no.kokab.myBlog.model.post.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @NonNull
    Page<PostEntity> findAll(@NonNull Pageable pageable);

    @Nullable
    PostEntity findByPostId(long postId);

    void deleteByPostId(Long postId);
}
