package com.project.consolecrud.service;

import com.project.consolecrud.model.Label;
import com.project.consolecrud.model.Post;
import com.project.consolecrud.model.PostStatus;
import com.project.consolecrud.model.Writer;
import com.project.consolecrud.repository.PostRepositry;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class PostService {
    private PostRepositry repository;

    public PostService(PostRepositry repository) {
        this.repository = repository;
    }

    public void addPost(Post post) {
        try {
            repository.save(post);
            System.out.println("Post successfully added.");
        } catch (Exception e) {
            System.out.println("Something wrong! Post not added");
        }
    }

    public void addPostWriterDep(Post post, Writer writer) {
        try {
            repository.savePostByWriter(post, writer);
        } catch (Exception e) {
            System.out.println("post_writer dependency not added!");
        }
    }

    public List<Post> findAll() {
        List<Post> postList = repository.findAll();

        if (postList.isEmpty()) {
            return Collections.emptyList();
        }
        return postList;
    }

    public List<Post> findByWriter(Writer writer) throws NullPointerException {
        List<Post> posts = repository.findAllByWriterName(writer);
        if (Objects.isNull(writer)) {
            System.out.println("Writer not found");
            return null;
        }
        return posts;
    }

    public Post findById(Long id) {
        Post post = repository.findById(id);
        if (Objects.isNull(post)) {
            System.out.println("Writer not found");
            return null;
        }
        return post;
    }

    public Post findByContent(String content) {
        Post post = repository.findPostByContent(content);
        if (Objects.isNull(post)) {
            System.out.println("This post is not exist");
            return null;
        }
        return post;
    }

    public List<Label> findLabelsForPost(Post post) {
        List<Label> labels = repository.findLabelsByPostId(post);
        if (labels.isEmpty()) {
            System.out.printf("Post with id = %d don't have labels yet.%n", post.getId());
            return Collections.emptyList();
        }
        return labels;
    }

    public List<Post> findPostsByLabelId(Label label) {
        List<Post> posts = repository.findPostByLabelId(label);
        if (posts.isEmpty()) {
            System.out.printf("This label with id: %d dont added to any post%n", label.getId());
            return Collections.emptyList();
        }
        return posts;
    }

    public void updatePost(Post post) {
        try {
            repository.update(post);
            System.out.printf("Post with id: %d - successfully updated%n",
                    post.getId());
        } catch (Exception e) {
            System.out.println("Something wrong, operation aborted!");
            System.out.println(e.getMessage());
        }
    }

    public void updatePostStatus(PostStatus status, Post post) {
        try {
            repository.updatePostStatus(status, post);
            System.out.printf("Post status with id: %d - successfully updated%n",
                    post.getId());
        } catch (Exception e) {
            System.out.println("Something wrong, operation aborted!");
            System.out.println(e.getMessage());
        }
    }

    public void deletePostById(Long id) {
        try {
            repository.deleteById(id);
            System.out.printf("Post with id: %d - successfully deleted from DB%n", id);
        }  catch (Exception e) {
            System.out.println("Something wrong, operation aborted!");
            System.out.println(e.getMessage());
        }
    }
}
