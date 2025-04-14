package no.kokab.myBlog.controller;

import jakarta.validation.Valid;
import no.kokab.myBlog.model.blog.PostEntity;
import no.kokab.myBlog.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@Validated
public class PostController {

    private final static int MAX_POST_LIMIT = 50;
    private final static String DEFAULT_POST_LIMIT = "25";

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    // query parameters: limit, offset both are optional
    public List<PostEntity> getPosts(
            @RequestParam(defaultValue = DEFAULT_POST_LIMIT) int limit,
            @RequestParam(defaultValue = "0") int offset) {

        return postService.getPosts(Math.min(limit, MAX_POST_LIMIT), offset);
    }

    @GetMapping("/{postId}")
    public PostEntity getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @PostMapping
    public PostEntity createPost(@Valid @RequestBody PostEntity post) {
        return postService.createPost(post);
    }

    @PutMapping("/{postId}")
    public PostEntity updatePost(@PathVariable Long postId, @Valid @RequestBody PostEntity post) {
        return postService.updatePost(postId, post);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        return postService.deletePost(postId)
            ? ResponseEntity.ok(String.format("post with id %d deleted", postId))
            : ResponseEntity.notFound().build();
    }

    // TODO: Add a The following functionality:
    @GetMapping("/search")
    public String searchPosts(@RequestParam String query) {
        // Logic to search for posts
        return "Search results for query: " + query;
    }

    @GetMapping("/category/{categoryId}")
    public String getPostsByCategory(@PathVariable Long categoryId) {
        // Logic to retrieve posts by category
        return "List of posts for category ID: " + categoryId;
    }
}
