package com.sysc.bulag_faust.post.service.post;

import com.sysc.bulag_faust.post.dto.post.AddPostRequest;
import com.sysc.bulag_faust.post.dto.post.PostResponse;
import com.sysc.bulag_faust.post.dto.post.UpdatePostRequest;
import com.sysc.bulag_faust.post.entities.Post;
import com.sysc.bulag_faust.post.entities.Status;
import com.sysc.bulag_faust.post.exception.UserNotFoundException;
import com.sysc.bulag_faust.post.mapper.PostMapper;
import com.sysc.bulag_faust.post.repository.PostRepository;
import com.sysc.bulag_faust.user.entities.User;
import com.sysc.bulag_faust.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {

    // Assuming you have a repository to handle database operations
    // private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public PostResponse addPost(AddPostRequest addRequestDTO) {
        if (addRequestDTO == null) {
            throw new NullPointerException();
        }

        Post post = new Post();
        post.setTitle(addRequestDTO.getTitle());
        post.setContent(addRequestDTO.getContent());
        //post.setUser(userService.getUserEntityById(addRequestDTO.getUserId())); //FIXME
        User user = new User();
        userRepository.save(user);
        post.setUser(user);
        postRepository.save(post);

        return postMapper.toDTO(post); // Replace with actual implementation
    }

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

    @Override
    public List<PostResponse> getAllPostsByUser(UUID userId) {
        List<Post> post = postRepository.findAllByUser_Id(userId);
        return postMapper.toListDTO(post);
    }

    @Override
    public List<PostResponse> getAllPosts() {
        return postMapper.toListDTO(postRepository.findAll());
    }

    @Override
    public List<PostResponse> getPostsByStatus(Status status) {
        List<Post> post = postRepository.findAllByStatus(status);

        return postMapper.toListDTO(post); // Replace with actual implementation
    }
}
