package com.project.consolecrud.repository;

import com.project.consolecrud.model.Writer;
import com.project.consolecrud.utils.DBConnector;
import com.project.consolecrud.utils.SQLQuery;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class WriterRepositoryImpl implements WriterRepository{
    private DBConnector db;
    private PostRepositry postRepositry;

    public WriterRepositoryImpl(PostRepositry postRepositry, DBConnector db) {
        this.db = db;
        this.postRepositry = postRepositry;
    }

    private Writer createWriter(ResultSet rs) throws SQLException {
        Writer writer = new Writer();
        writer.setId(rs.getLong("id"));
        writer.setFirstName(rs.getString("first_name"));
        writer.setLastName(rs.getString("last_name"));
        writer.setPosts(postRepositry.findAllByWriterName(writer));
        return writer;
    }


    @Override
    public void save(Writer entity) {
        try (Connection connection = db.getConnection();
            PreparedStatement pS = connection.prepareStatement(SQLQuery.INSERT_WRITER)) {
            pS.executeQuery();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Writer> findAll() {
        List<Writer> writers = new ArrayList<>();
        try (Connection connection = db.getConnection();
             PreparedStatement pS = connection.prepareStatement(SQLQuery.selectAll("writers"))) {
            ResultSet rs = pS.executeQuery();
            while (rs.next()) {
                writers.add(createWriter(rs));
            }
            connection.commit();
            rs.close();
            return writers;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Writer findById(Long id) {
        try (Connection connection = db.getConnection();
            PreparedStatement pS = connection.prepareStatement(SQLQuery.selectById("writers"))) {
            pS.setLong(1, id);
            ResultSet rs = pS.executeQuery();
            connection.commit();
            Writer writer = createWriter(rs);
            rs.close();
            return writer;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void update(Writer entity) {
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.UPDATE_WRITER)) {
            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setLong(3, entity.getId());
            ResultSet rs = ps.executeQuery();
            connection.commit();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.deleteById("writers"))) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            connection.commit();
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Writer findByName(String firstName, String lastName) {
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.SELECT_WRITER_BY_NAME)) {
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ResultSet rs = ps.executeQuery();
            Writer writer = createWriter(rs);
            connection.commit();
            rs.close();
            return writer;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
