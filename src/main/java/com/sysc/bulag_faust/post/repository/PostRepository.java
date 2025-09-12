package com.sysc.bulag_faust.post.repository;

import com.sysc.bulag_faust.post.domain.entities.Post;
import com.sysc.bulag_faust.post.domain.entities.PostStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    // With eager loading - different method name
    @EntityGraph(value = "graph.PostAuthor")
    List<Post> findAllByStatus(PostStatus status);

    // With eager loading
    @EntityGraph(value = "graph.PostAuthor")
    List<Post> findAllByAuthorId(UUID authorId);

    @EntityGraph(value = "graph.PostAuthor")
    Optional<Post> findPostById(UUID id);

    @EntityGraph(value = "graph.PostAuthor")
    @Override
    //bad practice to override findAll with eager loading
    List<Post> findAll();
}
