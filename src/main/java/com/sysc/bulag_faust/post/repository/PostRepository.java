package com.sysc.bulag_faust.post.repository;

import com.sysc.bulag_faust.post.entities.Post;
import com.sysc.bulag_faust.post.entities.PostStatus;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findAllByUser_Id(UUID userId);

    List<Post> findAllByStatus(PostStatus status);
}
