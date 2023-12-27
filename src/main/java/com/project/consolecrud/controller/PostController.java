package com.project.consolecrud.controller;

import com.project.consolecrud.model.Label;
import com.project.consolecrud.model.Post;
import com.project.consolecrud.model.PostStatus;
import com.project.consolecrud.model.Writer;
import com.project.consolecrud.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class PostController {
    private PostService service;

    public PostController(PostService service) {
        this.service = service;
    }


    public void createNewPost(Post post) {
        this.service.addPost(post);
    }

    public void addPostWriterDep(Post post, Writer writer) {
        this.service.addPostWriterDep(post, writer);
    }

    public List<Post> findAllPosts() {
        return this.service.findAll();
    }

    public Post findPostById(Long id) {
        return this.service.findById(id);
    }

    public Post findByPostContent(String content) {
        return this.service.findByContent(content);
    }

    public List<Post> findPostByWriter(Writer writer) {
        return this.service.findByWriter(writer);
    }

    public List<Post> findPostsByLabelId(Label label) {
        return this.service.findPostsByLabelId(label);
    }

    public List<Label> findLabelForPost(Post post) {
        return this.service.findLabelsForPost(post);
    }


    public void updatePostContent(Post post) {
        this.service.updatePost(post);
    }

    public void updatePostStatus(PostStatus status, Post post) {
        this.service.updatePostStatus(post.getStatus(), post);
    }

    public void deletePostFromDb(Long id) {
        this.service.deletePostById(id);
    }

}
