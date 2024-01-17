package com.project.consolecrud.repository;

import com.project.consolecrud.utils.Mapper;
import com.project.consolecrud.model.Post;
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
    private Mapper mapper;

    public WriterRepositoryImpl(PostRepositry postRepositry, DBConnector db, Mapper mapper) {
        this.db = db;
        this.postRepositry = postRepositry;
        this.mapper = mapper;
    }


    @Override
    public Writer save(Writer entity) {

        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.INSERT_WRITER)) {
            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Writer writer = findByName(entity.getFirstName(), entity.getLastName());
        writer.setPosts(postRepositry.findAllByWriterName(writer));
        return writer;
    }

    @Override
    public List<Writer> findAll() {
        List<Writer> writers = new ArrayList<>();
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLQuery.selectAll("writers"));
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                writers.add(mapper.createWriter(rs));
            }
            connection.commit();
            return writers;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Writer findById(Long id) {
        Writer writer = null;
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLQuery.selectById("writers"))) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    writer = mapper.createWriter(rs);
                    writer.setPosts(postRepositry.findAllByWriterName(writer));
                }
            }
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return writer;
    }

    @Override
    public Writer update(Writer entity) {
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.UPDATE_WRITER)) {
            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setLong(3, entity.getId());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return findById(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.deleteById("writers"))) {
            ps.setLong(1, id);
            ps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Writer findByName(String firstName, String lastName) {
        Writer writer = null;
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.SELECT_WRITER_BY_NAME)) {

            ps.setString(1, firstName);
            ps.setString(2, lastName);

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    writer = mapper.createWriter(rs);
                    writer.setPosts(postRepositry.findAllByWriterName(writer));
                }
            }
            connection.commit();
            return writer;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return writer;
        }
    }

    @Override
    public Writer findWriterByPost(Post post) {
        Writer writer = null;
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.SELECT_WRITER_BY_POST)) {

            ps.setLong(1, post.getId());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    writer = mapper.createWriter(rs);
                    writer.setPosts(postRepositry.findAllByWriterName(writer));
                }
            }
            connection.commit();
            return writer;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
