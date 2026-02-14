package com.sysc.bulag_faust.tag;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sysc.bulag_faust.tag.dto.TagResponse;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {

  // INFO: DO NOT FORGET TO CHANGE TH FILE PATH BELOW TOO
  @Query("""
      SELECT new com.sysc.bulag_faust.tag.dto.TagResponse

      ( t.id,
      t.name,
      COUNT(p)
      )
      FROM Tag t
      LEFT JOIN t.posts p ON p.status = 'PUBLISHED'
      WHERE t.id = :id
      GROUP BY t.id, t.name
      """)
  Optional<TagResponse> findTagByIdWithPostCount(@Param("id") UUID id);

  @Query("""
      SELECT new com.sysc.bulag_faust.tag.dto.TagResponse

      ( t.id,
      t.name,
      COUNT(p)
      )
      FROM Tag t
      LEFT JOIN t.posts p ON p.status = 'PUBLISHED'
      GROUP BY t.id, t.name
      """)
  List<TagResponse> findAllWithPostCount();

  boolean existsByIdAndPostsIsNotEmpty(UUID id);

}
