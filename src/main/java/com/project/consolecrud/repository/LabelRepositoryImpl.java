package com.project.consolecrud.repository;

import com.project.consolecrud.model.Label;
import com.project.consolecrud.model.Post;
import com.project.consolecrud.utils.DBConnector;
import com.project.consolecrud.utils.Mapper;
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
    private Mapper mapper;

    public LabelRepositoryImpl(DBConnector db, Mapper mspper) {
        this.db = db;
        this.mapper = mspper;
    }


    @Override
    public Label save(Label entity) {
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLQuery.INSERT_LABEL)) {
            ps.setString(1, entity.getName());
            ps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return findLabelByName(entity.getName());
    }

    @Override
    public void saveLabelByPost(Label label, Post post) {
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.INSERT_LABEL_BY_POST)) {
            ps.setLong(1, post.getId());
            ps.setLong(2, label.getId());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Label findLabelByName(String name) {
        Label label = null;
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.SELECT_LABEL_BY_NAME)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    label = mapper.createLabel(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return label;
    }

    @Override
    public List<Label> findAll() {
        List<Label> labels = new ArrayList<>();
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.selectAll("labels"));
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                labels.add(mapper.createLabel(rs));
            }

            connection.commit();
            return labels;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Label findById(Long id) {
        Label label = null;
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.selectById("labels"))) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    label = mapper.createLabel(rs);
                }
            }

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

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    labels.add(mapper.createLabel(rs));
                }
            }

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
            ps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.deleteById("labels"))) {
            ps.setLong(1, id);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
