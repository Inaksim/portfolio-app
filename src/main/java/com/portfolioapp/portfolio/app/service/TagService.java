package com.portfolioapp.portfolio.app.service;

import com.portfolioapp.portfolio.app.enitity.Tag;

import java.util.List;

public interface TagService {
    Tag createTag(String name);

    Tag getTagByName(String name);

    List<Tag> getAllTags();
}
