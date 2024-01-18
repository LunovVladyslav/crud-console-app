package com.project.consolecrud.service;

import com.project.consolecrud.model.Post;
import com.project.consolecrud.model.Writer;
import com.project.consolecrud.repository.WriterRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static com.project.consolecrud.utils.MapperTest.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WriterServiceTest {
    @InjectMocks
    WriterService writerService;
    @Mock
    WriterRepository writerRepository;

    private Writer writer;
    private Post post;

    @BeforeEach
    void setUp() {
        writer = createWriter();
        post = createPost();
    }

    @Test
    void shouldCreateNewWriter() {
        when(writerRepository.save(writer)).thenReturn(writer);
        writerService.addWriter(writer);

        verify(writerRepository).save(writer);
    }

    @Test
    void shouldFindByName() {

        when(writerRepository.findByName(FIRST_NAME, LAST_NAME)).thenReturn(writer);

        Writer result = writerService.findByName(FIRST_NAME, LAST_NAME);

        verify(writerRepository).findByName(FIRST_NAME, LAST_NAME);
        Assertions.assertEquals(result, writer);
    }

    @Test
    void shouldFindAllWriters_emptyList() {
        when(writerRepository.findAll()).thenReturn(Collections.emptyList());

        List<Writer> result = writerService.findAll();

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void shouldFindByPost() {

        when(writerRepository.findWriterByPost(post)).thenReturn(writer);

        Writer result = writerService.findWriterByPost(post);

        Assertions.assertEquals(result, writer);
        verify(writerRepository).findWriterByPost(post);
    }

    @Test
    void shouldUpdate() {
        writer.setFirstName("anotherName");

        doNothing().when(writerRepository).update(writer);

        writerService.updateWriter(writer);
        Assertions.assertNotEquals(FIRST_NAME, writer.getFirstName());
        verify(writerRepository).update(writer);
    }

    @Test
    void deleteWriterById() {
        doNothing().when(writerRepository).deleteById(ID);

        writerService.deleteWriterById(ID);

        verify(writerRepository).deleteById(ID);

    }



}