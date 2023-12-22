package com.project.consolecrud.repository;

import com.project.consolecrud.model.Label;
import com.project.consolecrud.model.Post;

import java.util.List;

public interface LabelRepository extends GenericRepository<Label, Long> {
    List<Label> findAllByPostId(Post entity);
    void saveLabelByPost(Label label, Post post);
}
