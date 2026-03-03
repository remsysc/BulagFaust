package com.sysc.bulag_faust.tag.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.sysc.bulag_faust.tag.Tag;
import com.sysc.bulag_faust.tag.dto.TagResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

  TagResponse toResponse(Tag tag);
}
