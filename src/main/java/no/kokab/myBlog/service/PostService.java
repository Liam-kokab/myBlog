package no.kokab.myBlog.service;

import no.kokab.myBlog.model.post.PostEntity;
import no.kokab.myBlog.model.post.PostRespond;
import no.kokab.myBlog.repository.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    public PostService(PostRepository postRepository, UserService userService, CategoryService categoryService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    private PostRespond convertToPostRespond(PostEntity post) {
        return new PostRespond(
            post.getPostId(),
            post.getTitle(),
            post.getContent(),
            categoryService.getCategoryById(post.getCategoryId()),
            userService.getUserPublicProfileById(post.getUserId()),
            post.getTags(),
            post.getCreatedAt(),
            post.getUpdatedAt()
        );
    }

    public List<PostRespond> getPosts(int limit, int offset) {
        Pageable pageable = PageRequest.of(offset, limit);
        List<PostEntity> postEntities = postRepository.findAll(pageable).getContent();

        return postEntities.stream()
            .map(this::convertToPostRespond)
            .toList();
    }

    public PostRespond getPostById(Long postId) {
        PostEntity postEntity = postRepository.findByPostId(postId);
        if (postEntity == null) {
            throw new RuntimeException("Post not found");
        }

        return convertToPostRespond(postEntity);
    }

    public PostRespond createPost(PostEntity post) {
        PostEntity postEntity = postRepository.save(post);

        return convertToPostRespond(postEntity);
    }

    public PostRespond updatePost(Long userId, PostEntity post) {
        PostEntity existingPost = postRepository.findById(post.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Check if the user ID matches the authenticated user ID or if the user is an admin
        if (!existingPost.getUserId().equals(userId) && !userService.isAdmin(userId)) {
            throw new RuntimeException("You are not authorized to update this post");
        }

        PostEntity postEntity = postRepository.save(new PostEntity(
            existingPost.getPostId(),
            post.getTitle(),
            post.getContent(),
            post.getCategoryId(),
            existingPost.getUserId(),
            post.getTags(),
            existingPost.getCreatedAt(),
            LocalDateTime.now(ZoneOffset.UTC)
        ));

        return convertToPostRespond(postEntity);
    }

    public boolean deletePost(Long postId) {
        if (!postRepository.existsById(postId)) {
            return false;
        }

        postRepository.deleteByPostId(postId);
        return true;
    }
}
