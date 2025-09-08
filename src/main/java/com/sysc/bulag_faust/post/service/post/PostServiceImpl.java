package com.sysc.bulag_faust.post.service.post;

import com.sysc.bulag_faust.core.exception.user.UserNotFoundException;
import com.sysc.bulag_faust.post.dto.post.AddPostRequest;
import com.sysc.bulag_faust.post.dto.post.PostResponse;
import com.sysc.bulag_faust.post.dto.post.UpdatePostRequest;
import com.sysc.bulag_faust.post.entities.Post;
import com.sysc.bulag_faust.post.entities.PostStatus;
import com.sysc.bulag_faust.post.mapper.PostMapper;
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
public class PostServiceImpl implements PostService {

    // Assuming you have a repository to handle database operations
    // private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    //private final UserRepository userRepository;
    private final UserServiceImpl userService;

    //all post methods always with author

    @Override
    public PostResponse addPost(
        AddPostRequest addRequestDTO,
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
    public void deletePost(UUID id) {
        //TODO: must be deleted by auth user

        postRepository.delete(getPostEntityById(id));
    }

    //n+1 problem
    // @Override
    // public List<PostResponse> getAllPostsByAuthor(UUID userId) {
    //     List<Post> post = postRepository.findAllByAuthor_Id(userId);
    //     return postMapper.toListDTO(post);
    // }

    @Override
    public List<PostResponse> getPostsByStatus(PostStatus status) {
        List<Post> post = postRepository.findAllByStatus(status);

        return postMapper.toListDTO(post); // Replace with actual implementation
    }

    @Override
    //method that dont need author data
    public List<PostResponse> getAllPosts() {
        return postMapper.toListDTO(postRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponse getPostWithAuthor(UUID postId) {
        Post post = getPostEntityById(postId);

        @SuppressWarnings("unused")
        //access author within transaction to trigger lazy loading
        String authorName = post.getAuthor() != null
            ? post.getAuthor().getName()
            : "Unknown Author";
        return postMapper.toDTO(post);
    }

    @Override
    public List<PostResponse> getAllPostsByAuthorIdWithAuthor(UUID authorId) {
        List<Post> posts = postRepository.findAllByAuthorId(authorId);
        return postMapper.toListDTO(posts);
    }
}
