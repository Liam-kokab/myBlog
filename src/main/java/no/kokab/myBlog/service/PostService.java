package no.kokab.myBlog.service;

import no.kokab.myBlog.model.post.PostEntity;
import no.kokab.myBlog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostEntity> getPosts(int limit, int offset) {
        Pageable pageable = PageRequest.of(offset, limit);
        return postRepository.findAll(pageable).getContent();
    }

    public PostEntity getPostById(Long postId) {
        return postRepository.findByPostId(postId);
    }

    public PostEntity createPost(PostEntity post) {
        return postRepository.save(post);
    }

    public PostEntity updatePost(Long postId, PostEntity post) {
        PostEntity existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        existingPost.setCategoryId(post.getCategoryId());

        return postRepository.save(existingPost);
    }

    public boolean deletePost(Long postId) {
        if (!postRepository.existsById(postId)) {
            return false;
        }

        postRepository.deleteByPostId(postId);
        return true;
    }
}
