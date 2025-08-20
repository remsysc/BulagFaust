package com.sysc.bulag_faust.post.repository;

import com.sysc.bulag_faust.post.entities.Post;
import com.sysc.bulag_faust.post.entities.Status;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findAllByUserId(UUID userId);

    List<Post> findAllByStatus(Status status);
}
