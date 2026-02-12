package com.sysc.bulag_faust.tag.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.sysc.bulag_faust.tag.Tag;
import com.sysc.bulag_faust.tag.dto.TagResponse;

@Mapper(componentModel = "spring")
public interface TagMapper {

  TagResponse toResponse(Tag tag);

  List<TagResponse> toResponses(List<Tag> tags);

}
