package com.sysc.bulag_faust.post.controllers;

import com.sysc.bulag_faust.core.response.ApiResponse;
import com.sysc.bulag_faust.post.dto.PostDTO;
import com.sysc.bulag_faust.post.service.IPostService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("${api.prefix}/post")
public class PostControllers {

    private final IPostService postService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostDTO>>> getPosts() {
        List<PostDTO> post = postService.getAllPosts();
        return ResponseEntity.status(HttpStatus.OK).body(
            ApiResponse.success("getPosts success: ", post)
        );
    }

    @GetMapping("/{userId")
    public ResponseEntity<ApiResponse<List<PostDTO>>> getPostByUser(
        @Valid @RequestParam UUID userId
    ) {
        List<PostDTO> post = postService.getAllPostsByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(
            ApiResponse.success("Get all post by user is successful: ", post)
        );
    }
}
