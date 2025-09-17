package com.sysc.bulag_faust.post.controllers;

import com.sysc.bulag_faust.core.response.ApiResponse;
import com.sysc.bulag_faust.post.domain.dto.post.CreatePostRequest;
import com.sysc.bulag_faust.post.domain.dto.post.PostResponse;
import com.sysc.bulag_faust.post.service.post.PostService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("${api.prefix}/post")
public class PostControllers {

    private final PostService postService;

    @GetMapping
    @NotNull
    public ResponseEntity<ApiResponse<List<PostResponse>>> getPosts() {
        List<PostResponse> post = postService.getAllPosts();
        return ResponseEntity.status(HttpStatus.OK).body(
            ApiResponse.success("getPosts success: ", post)
        );
    }

    @GetMapping("/{authorId}")
    @NotNull
    public ResponseEntity<ApiResponse<List<PostResponse>>> getAllPostsByUser(
        @Valid @PathVariable UUID authorId
    ) {
        List<PostResponse> post = postService.getAllPostsByAuthorId(authorId);
        return ResponseEntity.status(HttpStatus.OK).body(
            ApiResponse.success("Get all post by user is successful: ", post)
        );
    }

    @PostMapping
    @NotNull
    public ResponseEntity<ApiResponse<PostResponse>> createPost(
        @Valid CreatePostRequest request,
        @Valid @PathVariable UUID authorId
    ) {
        PostResponse postDTO = postService.createPost(request, authorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.success("Post created: ", postDTO)
        );
    }
}
