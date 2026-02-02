package com.sysc.bulag_faust.post;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sysc.bulag_faust.post.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
}
