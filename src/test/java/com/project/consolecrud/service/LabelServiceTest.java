package com.project.consolecrud.service;

import com.project.consolecrud.model.Label;
import com.project.consolecrud.model.Post;
import com.project.consolecrud.repository.LabelRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static com.project.consolecrud.utils.MapperTest.*;
@ExtendWith(MockitoExtension.class)
class LabelServiceTest {
    @InjectMocks
    LabelService labelService;
    @Mock
    LabelRepository labelRepository;

    private Label label;
    private Post post;

    @BeforeEach
    void setUp() {
        label = createLabel();
        post = createPost();
    }


    @Test
    void shouldAddLabel() {
        when(labelRepository.save(label)).thenReturn(label);
        labelService.addLabel(label);
        verify(labelRepository).save(label);
    }

    @Test
    void addLabelForPost() {
        doNothing().when(labelRepository).saveLabelByPost(label, post);
        labelService.addLabelForPost(label, post);
        verify(labelRepository).saveLabelByPost(label, post);
    }

    @Test
    void findAll() {
        when(labelRepository.findAll()).thenReturn(Collections.emptyList());
        List<Label> result = labelService.findAll();
        Assertions.assertTrue(result.isEmpty());
        verify(labelRepository).findAll();
    }

    @Test
    void findById() {
        when(labelRepository.findById(ID)).thenReturn(label);
        Label result = labelService.findById(ID);
        Assertions.assertEquals(result, label);
        verify(labelRepository).findById(ID);
    }

    @Test
    void findByName() {
        when(labelRepository.findLabelByName(FIRST_NAME)).thenReturn(label);
        Label result = labelService.findByName(FIRST_NAME);
        Assertions.assertEquals(result, label);
        verify(labelRepository).findLabelByName(FIRST_NAME);
    }

    @Test
    void updateLabel() {
        doNothing().when(labelRepository).update(label);
        labelService.updateLabel(label);
        verify(labelRepository).update(label);
    }

    @Test
    void deleteById() {
        doNothing().when(labelRepository).deleteById(ID);
        labelService.deleteById(ID);
        verify(labelRepository).deleteById(ID);

    }
}