package com.sysc.bulag_faust.post.domain.mapper;

import com.sysc.bulag_faust.post.domain.dto.tag.TagResponse;
import com.sysc.bulag_faust.post.domain.entities.Tag;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagResponse toDTO(Tag tag);
    List<TagResponse> toListDTO(List<Tag> tags);
}
