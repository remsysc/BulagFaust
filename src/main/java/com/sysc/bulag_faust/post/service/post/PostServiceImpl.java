package com.sysc.bulag_faust.post.service.post;

import com.sysc.bulag_faust.core.exception.user.UserNotFoundException;
import com.sysc.bulag_faust.post.domain.dto.post.CreatePostRequest;
import com.sysc.bulag_faust.post.domain.dto.post.PostResponse;
import com.sysc.bulag_faust.post.domain.dto.post.UpdatePostRequest;
import com.sysc.bulag_faust.post.domain.entities.Post;
import com.sysc.bulag_faust.post.domain.entities.PostStatus;
import com.sysc.bulag_faust.post.domain.mapper.PostMapper;
import com.sysc.bulag_faust.post.repository.PostRepository;
import com.sysc.bulag_faust.user.entities.User;
import com.sysc.bulag_faust.user.service.UserServiceImpl;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final UserServiceImpl userService;

    @Override
    @Transactional
    public PostResponse createPost(
        CreatePostRequest addRequestDTO,
        UUID currentUserId
    ) {
        if (addRequestDTO == null) {
            throw new IllegalArgumentException("Post request cannot be null");
        }
        //temp fix
        User author = userService.getUserEntityById(currentUserId);

        Post post = new Post();
        post.setTitle(addRequestDTO.getTitle());
        post.setContent(addRequestDTO.getContent());
        post.setAuthor(author);

        return postMapper.toDTO(postRepository.save(post)); // Replace with actual implementation
    }

    // private User getCurrentAuthenticatedUser(){
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     String username = authentication.getName();
    //     return userService.getUserByUsername(username);
    // }

    @Override
    @Transactional
    public PostResponse updatePost(UpdatePostRequest request) {
        Post post = getPostEntityById(request.getId());
        post.setContent(request.getContent());
        post.setTitle(request.getTitle());
        post.setUpdatedAt(LocalDateTime.now());
        postRepository.save(post);

        return postMapper.toDTO(post); // Replace with actual implementation
    }

    @Override
    public Post getPostEntityById(UUID id) {
        return postRepository
            .findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found")); // Replace with actual implementation
    }

    @Override
    @Transactional
    public void deletePost(UUID id) {
        //TODO: must be deleted by auth user

        postRepository.delete(getPostEntityById(id));
    }

    @Override
    public List<PostResponse> getPostsByStatus(PostStatus status) {
        List<Post> post = postRepository.findAllByStatus(status);

        return postMapper.toListDTO(post); // Replace with actual implementation
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postMapper.toListDTO(postRepository.findAll());
    }

    @Override
    public PostResponse getPostWithAuthorId(UUID postId) {
        Post post = postRepository
            .findPostById(postId)
            .orElseThrow(() -> new UserNotFoundException("Post not found"));

        return postMapper.toDTO(post);
    }

    @Override
    public List<PostResponse> getAllPostsByAuthorId(UUID authorId) {
        List<Post> posts = postRepository.findAllByAuthorId(authorId);
        return postMapper.toListDTO(posts);
    }
}
