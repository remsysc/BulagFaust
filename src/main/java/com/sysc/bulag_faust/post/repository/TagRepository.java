package com.sysc.bulag_faust.post.repository;

import com.sysc.bulag_faust.post.domain.entities.Tag;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {
    Tag findByName(String name);
}
