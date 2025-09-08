package com.sysc.bulag_faust.post.repository;

import com.sysc.bulag_faust.post.entities.Post;
import com.sysc.bulag_faust.post.entities.PostStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findAllByStatus(PostStatus status);

    @Query("SELECT p FROM Post p JOIN FETCH p.author WHERE p.status = :status")
    List<Post> findAllByStatusWithAuthors(PostStatus status);

    @Query("SELECT p FROM Post p JOIN FETCH p.author")
    List<Post> findAllWithAuthors();

    @Query("SELECT p FROM Post p WHERE p.status = 'PUBLISHED' ")
    List<Post> findAllPublishedPosts();

    @Query("SELECT p FROM Post p JOIN FETCH p.author WHERE p.id = :id")
    List<Post> findAllByAuthorIdWithAuthor(UUID userId);

    // @Query("SELECT p FROM POST p JOIN FETCH p.author WHERE p.id = :id")
    // Post findIdWithAuthor(UUID id);
}
