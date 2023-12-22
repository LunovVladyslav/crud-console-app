package com.project.consolecrud.repository;

import com.project.consolecrud.model.Label;
import com.project.consolecrud.model.Post;
import com.project.consolecrud.utils.DBConnector;
import com.project.consolecrud.utils.SQLQuery;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class LabelRepositoryImpl implements LabelRepository{
    private DBConnector db;

    public LabelRepositoryImpl(DBConnector db) {
        this.db = db;
    }

    private Label createLabel(ResultSet rs) throws SQLException {
        Label label = new Label();
        label.setId(rs.getLong("id"));
        label.setName(rs.getString("name"));
        return label;
    }

    @Override
    public void save(Label entity) {
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLQuery.INSERT_LABEL)) {
            ps.setString(1, entity.getName());
            ps.executeQuery();
            connection.commit();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveLabelByPost(Label label, Post post) {
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.INSERT_LABEL_BY_POST)) {
            ps.setLong(1, post.getId());
            ps.setLong(2, label.getId());
            ps.executeQuery();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<Label> findAll() {
        List<Label> labels = new ArrayList<>();
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.selectAll("label"))) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                labels.add(createLabel(rs));
            }
            rs.close();
            connection.commit();
            return labels;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Label findById(Long id) {
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.selectById("label"))) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            Label label = createLabel(rs);
            rs.close();
            connection.commit();
            return label;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Label> findAllByPostId(Post entity) {
        List<Label> labels = new ArrayList<>();
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.SELECT_LABELS_BY_POST)) {
            ps.setLong(1, entity.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                labels.add(createLabel(rs));
            }
            rs.close();
            connection.commit();
            return labels;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public void update(Label entity) {
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.UPDATE_LABEL)) {
            ps.setString(1, entity.getName());
            ps.setLong(2, entity.getId());
            ps.executeQuery();
            connection.commit();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.deleteById("label"))) {
            ps.setLong(1, id);
            ps.executeQuery();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
