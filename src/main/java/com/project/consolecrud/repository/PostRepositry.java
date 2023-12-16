package com.project.consolecrud.repository;

import com.project.consolecrud.model.Label;
import com.project.consolecrud.model.Post;
import com.project.consolecrud.model.Writer;

import java.util.List;

public interface PostRepositry extends GenericRepository<Post, Long> {
    List<Post> findAllByWriterName(Writer writer);
    List<Label> findLabelsByPostId(Post post);
}
