package com.portfolioapp.portfolio.app.service.impl;

import com.portfolioapp.portfolio.app.enitity.Tag;
import com.portfolioapp.portfolio.app.repository.TagRepository;
import com.portfolioapp.portfolio.app.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;

    @Override
    public Tag createTag(String name) {
        if (tagRepository.findByName(name).isPresent()) {
            throw new RuntimeException("Tag already exists");
        }
        Tag tag = new Tag();
        tag.setName(name);
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }
}
