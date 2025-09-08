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

    @EntityGraph(attributePaths = { "author" })
    List<Post> findAllByStatusWithAuthors(PostStatus status);

    @EntityGraph(attributePaths = { "author" })
    List<Post> findAll();

    @EntityGraph(attributePaths = { "author" })
    List<Post> findAllPublishedPosts();

    @EntityGraph(attributePaths = { "author" })
    List<Post> findAllByAuthorId(UUID authorId);

    @EntityGraph(attributePaths = { "author" })
    Optional<Post> findByIdWithAuthor(UUID postID);
}
