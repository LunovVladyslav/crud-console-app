package com.project.consolecrud.service;

import com.project.consolecrud.model.Post;
import com.project.consolecrud.model.Writer;
import com.project.consolecrud.repository.WriterRepository;
import com.project.consolecrud.utils.MapperTest;
import org.junit.jupiter.api.Assertions;
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

    @Test
    void shouldCreateNewWriter() {
        Writer writer = MapperTest.createWriter();

        doNothing().when(writerRepository).save(writer);

        writerService.addWriter(writer);

        verify(writerRepository).save(writer);
    }

    @Test
    void shouldFindByName() {
        Writer writer = MapperTest.createWriter();

        when(writerRepository.findByName(FIRST_NAME, LAST_NAME)).thenReturn(writer);

        Writer result = writerService.findByName(FIRST_NAME, LAST_NAME);

        verify(writerRepository).findByName(FIRST_NAME, LAST_NAME);
        Assertions.assertEquals(result, writer);
    }

    @Test
    void shouldFindAllWriters_emptyList() {
        when(writerRepository.findAll()).thenReturn(Collections.emptyList());

        List<Writer> writers = writerService.findAll();

        Assertions.assertTrue(writers.isEmpty());
    }

    @Test
    void shouldFindByPost() {
        Post post = MapperTest.createPost();
        Writer writer = MapperTest.createWriter();

        when(writerRepository.findWriterByPost(post)).thenReturn(writer);

        Writer result = writerService.findWriterByPost(post);

        Assertions.assertEquals(result, writer);
        verify(writerRepository).findWriterByPost(post);
    }

    @Test
    void deleteWriterById() {
        doNothing().when(writerRepository).deleteById(MapperTest.ID);

        writerService.deleteWriterById(ID);

        verify(writerRepository).deleteById(ID);

    }



}