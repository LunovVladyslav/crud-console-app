package com.project.consolecrud.repository;

import com.project.consolecrud.model.Label;
import com.project.consolecrud.model.Post;
import com.project.consolecrud.model.PostStatus;
import com.project.consolecrud.model.Writer;

import java.util.List;

public interface PostRepositry extends GenericRepository<Post, Long> {
    List<Post> findAllByWriterName(Writer writer);
    Post findPostByContent(String content);
    List<Label> findLabelsByPostId(Post post);
    List<Post> findPostByLabelId(Label label);
    void savePostByWriter(Post post, Writer writer);
    void updatePostStatus(PostStatus status, Post post);
}
