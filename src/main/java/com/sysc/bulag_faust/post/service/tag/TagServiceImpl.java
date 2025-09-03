package com.sysc.bulag_faust.post.service.tag;

import com.sysc.bulag_faust.core.exception.tag.TagAlreadyExist;
import com.sysc.bulag_faust.core.exception.tag.TagNotFound;
import com.sysc.bulag_faust.post.dto.tag.AddTagRequest;
import com.sysc.bulag_faust.post.dto.tag.TagResponse;
import com.sysc.bulag_faust.post.dto.tag.UpdateTagRequest;
import com.sysc.bulag_faust.post.entities.Tag;
import com.sysc.bulag_faust.post.mapper.TagMapper;
import com.sysc.bulag_faust.post.repository.TagRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public TagResponse addTag(AddTagRequest request) {
        //check existing name
        if (tagRepository.findByName(request.getName()) != null) {
            throw new TagAlreadyExist(request.getName());
        }
        Tag tag = new Tag(request.getName());
        return tagMapper.toDTO(tagRepository.save(tag));
    }

    public List<TagResponse> getAllTags() {
        return tagMapper.toListDTO(tagRepository.findAll());
    }

    @Override
    public TagResponse updateTag(UpdateTagRequest request) {
        //check valid id
        Tag tag = getTagEntityById(request.getId());
        //check existing name for conflict
        if (tagRepository.findByName(request.getName()) != null) {
            throw new TagAlreadyExist(request.getName());
        }

        tag.setName(request.getName());
        return tagMapper.toDTO(tagRepository.save(tag));
    }

    @Override
    public Tag getTagEntityById(UUID id) {
        Tag tag = tagRepository
            .findById(id)
            .orElseThrow(() -> new TagNotFound(id.toString()));
        return tag;
    }

    @Override
    public TagResponse getTagById(UUID id) {
        Tag tag = tagRepository
            .findById(id)
            .orElseThrow(() -> new TagNotFound(id.toString()));
        return tagMapper.toDTO(tag);
    }
}
