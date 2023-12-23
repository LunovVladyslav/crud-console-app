package com.project.consolecrud.controller;

import com.project.consolecrud.model.Label;
import com.project.consolecrud.model.Post;
import com.project.consolecrud.model.Writer;
import com.project.consolecrud.service.PostService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PostController {
    private PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    public void createNewPost(Post post, Writer writer) {
        this.service.addPost(post, writer);
    }

    public List<Post> findAllPosts() {
        return this.service.findAll();
    }

    public Post findPostById(Long id) {
        return this.service.findById(id);
    }

    public List<Post> findPostByWriter(Writer writer) {
        return this.service.findByWriter(writer);
    }

    public List<Label> findLabelForPost(Post post) {
        return this.service.findLabelsForPost(post);
    }

    public void updatePost(Post post) {
        this.service.updatePostStatus(post.getStatus(), post);
        this.service.updatePost(post);
    }

    public void deletePostFromDb(Long id) {
        this.service.deletePostById(id);
    }

}
