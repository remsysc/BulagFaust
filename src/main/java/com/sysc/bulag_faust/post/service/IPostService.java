package com.sysc.bulag_faust.post.service;

import com.sysc.bulag_faust.post.dto.PostDTO;
import com.sysc.bulag_faust.post.dto.request.AddRequestDTO;
import com.sysc.bulag_faust.post.dto.request.UpdateRequestDTO;
import com.sysc.bulag_faust.post.entities.Post;
import com.sysc.bulag_faust.post.entities.Status;
import java.util.List;
import java.util.UUID;

public interface IPostService {
    PostDTO addPost(AddRequestDTO addRequestDTO);
    PostDTO updatePost(UpdateRequestDTO request);
    Post getPostEntityById(UUID id);
    void deletePost(UUID id);
    List<PostDTO> getAllPostsByUser(UUID userId);
    List<PostDTO> getAllPosts();
    List<PostDTO> getPostsByStatus(Status status);
}
