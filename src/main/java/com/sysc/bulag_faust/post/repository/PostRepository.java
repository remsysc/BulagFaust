package com.sysc.bulag_faust.post.repository;

import com.sysc.bulag_faust.post.entities.Post;
import com.sysc.bulag_faust.post.entities.PostStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findAllByStatus(PostStatus status);

    @EntityGraph(value = "graph.PostAuthor")
    List<Post> findAllByStatusWithAuthors(PostStatus status);

    @EntityGraph(value = "graph.PostAuthor")
    List<Post> findAll();

    @EntityGraph(value = "graph.PostAuthor")
    List<Post> findAllPublishedPosts();

    @EntityGraph(value = "graph.PostAuthor")
    List<Post> findAllByAuthorId(UUID authorId);

    @EntityGraph(value = "graph.PostAuthor")
    Optional<Post> findByIdWithAuthor(UUID postID);
}
