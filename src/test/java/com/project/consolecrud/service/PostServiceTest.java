package com.project.consolecrud.service;

import com.project.consolecrud.model.Label;
import com.project.consolecrud.model.Post;
import com.project.consolecrud.model.PostStatus;
import com.project.consolecrud.model.Writer;
import com.project.consolecrud.repository.PostRepositry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static com.project.consolecrud.utils.MapperTest.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @InjectMocks
    PostService postService;
    @Mock
    PostRepositry postRepositry;
    private Post post;
    private Writer writer;
    private Label label;
    private List<Label> labelList;
    private List<Post> postList;

    @BeforeEach
    void setUp() {
        post = createPost();
        writer = createWriter();
        label = createLabel();
        postList = Collections.emptyList();
        labelList = Collections.emptyList();
    }



    @Test
    void shouldAddPost() {

        when(postRepositry.save(post)).thenReturn(post);
        postService.addPost(post);
        verify(postRepositry).save(post);
    }

    @Test
    void shouldAddPostWriterDep() {

        doNothing().when(postRepositry).savePostByWriter(post, writer);
        postService.addPostWriterDep(post, writer);
        verify(postRepositry).savePostByWriter(post, writer);
    }

    @Test
    void shouldFindAll_emptyList() {
        when(postRepositry.findAll()).thenReturn(postList);
        List<Post> result = postService.findAll();
        Assertions.assertTrue(result.isEmpty());
        verify(postRepositry).findAll();
    }

    @Test
    void shouldFindByWriter_emptyList() {
        when(postRepositry.findAllByWriterName(writer)).thenReturn(postList);
        List<Post> result = postService.findByWriter(writer);
        Assertions.assertTrue(result.isEmpty());
        verify(postRepositry).findAllByWriterName(writer);
    }

    @Test
    void shouldFindById() {
        when(postRepositry.findById(ID)).thenReturn(post);
        Post result = postService.findById(ID);
        Assertions.assertEquals(result, post);
        verify(postRepositry).findById(ID);
    }

    @Test
    void shouldFindByContent() {
        when(postRepositry.findPostByContent(CONTENT)).thenReturn(post);
        Post result = postService.findByContent(CONTENT);
        Assertions.assertEquals(result, post);
        verify(postRepositry).findPostByContent(CONTENT);
    }

    @Test
    void shouldFindLabelsForPost() {
        when(postRepositry.findLabelsByPostId(post)).thenReturn(labelList);
        List<Label> result = postService.findLabelsForPost(post);
        Assertions.assertTrue(result.isEmpty());
        verify(postRepositry).findLabelsByPostId(post);
    }

    @Test
    void findPostsByLabelId() {
        when(postRepositry.findPostByLabelId(label)).thenReturn(postList);
        List<Post> result = postService.findPostsByLabelId(label);
        Assertions.assertTrue(result.isEmpty());
        verify(postRepositry).findPostByLabelId(label);
    }

    @Test
    void updatePost() {
        post.setUpdated(Date.valueOf(LocalDate.now()));
        doNothing().when(postRepositry).update(post);
        postService.updatePost(post);
        verify(postRepositry).update(post);
    }

    @Test
    void updatePostStatus() {
       doNothing().when(postRepositry).updatePostStatus(PostStatus.DELETED, post);
       postService.updatePostStatus(PostStatus.DELETED, post);
       verify(postRepositry).updatePostStatus(PostStatus.DELETED, post);
    }

    @Test
    void deletePostById() {
        doNothing().when(postRepositry).deleteById(ID);
        postService.deletePostById(ID);
        verify(postRepositry).deleteById(ID);
    }
}