package no.kokab.myBlog.controller;

import jakarta.validation.Valid;
import no.kokab.myBlog.model.post.CreatePost;
import no.kokab.myBlog.model.post.PostEntity;
import no.kokab.myBlog.model.post.PostRespond;
import no.kokab.myBlog.service.PostService;
import no.kokab.myBlog.util.AuthenticationUtil;
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
    public List<PostRespond> getPosts(
            @RequestParam(defaultValue = DEFAULT_POST_LIMIT) int limit,
            @RequestParam(defaultValue = "0") int offset) {

        return postService.getPosts(Math.min(limit, MAX_POST_LIMIT), offset);
    }

    @PostMapping
    public PostRespond createPost(@Valid @RequestBody CreatePost post) {
        // Get the authenticated user ID from the security context
        Long userId = AuthenticationUtil.getAuthenticatedUserId();

        return postService.createPost(new PostEntity(
            null,
            post.title(),
            post.content(),
            post.categoryId(),
            userId,
            post.tags(),
            null,
            null
        ));
    }

    @GetMapping("/{postId}")
    public PostRespond getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @PutMapping("/{postId}")
    public PostRespond updatePost(@PathVariable Long postId, @Valid @RequestBody CreatePost post) {
        Long userId = AuthenticationUtil.getAuthenticatedUserId();

        return postService.updatePost(userId, new PostEntity(
            postId,
            post.title(),
            post.content(),
            post.categoryId(),
            null,
            post.tags(),
            null,
            null
        ));
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

}
