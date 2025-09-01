package com.sysc.bulag_faust.post.mapper;

import com.sysc.bulag_faust.post.dto.tag.TagResponse;
import com.sysc.bulag_faust.post.entities.Tag;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagResponse toDTO(Tag tag);
    List<TagResponse> toListDTO(List<Tag> tags);
}
