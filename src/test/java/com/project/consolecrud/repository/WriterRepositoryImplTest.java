package com.project.consolecrud.repository;

import com.project.consolecrud.model.Writer;
import com.project.consolecrud.utils.DBConnector;
import com.project.consolecrud.utils.Mapper;
import com.project.consolecrud.utils.SQLQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static com.project.consolecrud.utils.MapperTest.*;

@ExtendWith(MockitoExtension.class)
class WriterRepositoryImplTest {
    @InjectMocks
    WriterRepositoryImpl writerRepository;
    @Mock
    private Connection connection;
    @Mock
    private DBConnector db;
    @Mock
    private Mapper mapper;
    @Mock
    private ResultSet resultSet;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private PostRepositry postRepositry;
    @Mock
    private SQLQuery sqlQuery;
    private final String tableName = "writers";

    @Test
    void testSave() throws SQLException {
        Writer writer = createWriter();

        when(db.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        when(connection.prepareStatement(SQLQuery.SELECT_WRITER_BY_NAME)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(mapper.createWriter(resultSet)).thenReturn(writer);

        Writer result = writerRepository.save(writer);

        verify(preparedStatement, times(2)).setString(1, writer.getFirstName());
        verify(preparedStatement, times(2)).setString(2, writer.getLastName());
        verify(preparedStatement, times(1)).executeUpdate();
        verify(preparedStatement, times(1)).executeQuery();
        verify(connection, times(2)).commit();

        // Assertions
        assertNotNull(result);
        assertEquals("name", result.getFirstName());
        assertEquals("lastName", result.getLastName());
    }

    @Test
    void testFindAll() throws SQLException {
        List<Writer> writers = Collections.emptyList();

        when(db.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);


        List<Writer> result = writerRepository.findAll();

        assertTrue(result.isEmpty());
        verify(preparedStatement, times(1)).executeQuery();
        verify(connection, times(1)).commit();

    }

    @Test
    void testFindById() throws SQLException {
        Writer writer = createWriter();
        when(db.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(mapper.createWriter(resultSet)).thenReturn(writer);

        Writer result = writerRepository.findById(ID);
        assertEquals(writer, result);
        verify(connection, times(1)).commit();
        verify(preparedStatement, times(1)).executeQuery();
    }

    @Test
    void testUpdate() throws SQLException {
        Writer writer = createWriter();
        when(db.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        writerRepository.update(writer);
        verify(connection, times(1)).commit();
        verify(db, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).executeUpdate();

    }


}