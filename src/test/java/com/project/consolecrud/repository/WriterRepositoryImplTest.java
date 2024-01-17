package com.project.consolecrud.repository;

import com.project.consolecrud.model.Writer;
import com.project.consolecrud.utils.DBConnector;
import com.project.consolecrud.utils.MapperTest;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WriterRepositoryImplTest {
    @InjectMocks
    WriterRepositoryImpl writerRepository;
    @Mock
    Connection connection;
    @Mock
    private DBConnector db;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private PostRepositry postRepositry;
    @Mock
    private SQLQuery sqlQuery;
    @Mock
    private ResultSet resultSet;
    @Mock
    private MapperTest mapper;

    private final String USER_FIRST_NAME = "name";
    private final String USER_LAST_NAME = "lastName";
    private final Long ID = 1L;

    @BeforeEach
    void setup() throws SQLException {
        when(db.getConnection()).thenReturn(connection);
    }

    @Test
    void shouldUpdate() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(connection.prepareStatement(SQLQuery.UPDATE_WRITER)).thenReturn(preparedStatement);
        writerRepository.update(getWriter());
        verify(db).getConnection();
    }

    @Test
    void findById() throws SQLException {
        when(connection.prepareStatement("statement")).thenReturn(preparedStatement);
        when(sqlQuery.selectById("writers")).thenReturn("statement");
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(mapper.createWriter()).thenReturn(getWriter());

        Writer writer = writerRepository.findById(ID);

        verify(db).getConnection();
        verify(resultSet).next();
        verify(sqlQuery).selectById("writers");
        assertEquals(USER_FIRST_NAME, writer.getFirstName());
        assertEquals(USER_LAST_NAME, writer.getLastName());
        assertEquals(ID, writer.getId());
    }



    private Writer getWriter() {
        Writer writer = new Writer();
        writer.setId(ID);
        writer.setFirstName(USER_FIRST_NAME);
        writer.setLastName(USER_LAST_NAME);
        return writer;
    }
}