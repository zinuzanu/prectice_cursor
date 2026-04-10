package com.example.board.post;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostApiController {

    private final PostService postService;

    public PostApiController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public Page<PostResponse> list(
            @RequestParam(required = false) String q,
            @PageableDefault(size = 10, sort = "createdAt", direction = Direction.DESC) Pageable pageable) {
        return postService.search(q, pageable).map(PostResponse::from);
    }

    @GetMapping("/{id}")
    public PostResponse detail(@PathVariable Long id) {
        return PostResponse.from(postService.findByIdOrThrow(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse create(@Valid @RequestBody PostCreateRequest request) {
        Post post = new Post();
        post.setTitle(request.title());
        post.setContent(request.content());
        return PostResponse.from(postService.create(post));
    }

    @PutMapping("/{id}")
    public PostResponse update(@PathVariable Long id, @Valid @RequestBody PostUpdateRequest request) {
        return PostResponse.from(postService.update(id, request.title(), request.content()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        postService.deleteById(id);
    }

    public record PostCreateRequest(
            @NotBlank(message = "{post.title.notBlank}") @Size(max = 200, message = "{post.title.size}") String title,
            @NotBlank(message = "{post.content.notBlank}") @Size(max = 10000, message = "{post.content.size}")
                    String content) {
    }

    public record PostUpdateRequest(
            @NotBlank(message = "{post.title.notBlank}") @Size(max = 200, message = "{post.title.size}") String title,
            @NotBlank(message = "{post.content.notBlank}") @Size(max = 10000, message = "{post.content.size}")
                    String content) {
    }

    public record PostResponse(Long id, String title, String content, LocalDateTime createdAt) {
        static PostResponse from(Post post) {
            return new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.getCreatedAt());
        }
    }
}

