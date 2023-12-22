package com.project.consolecrud.repository;

import com.project.consolecrud.model.Label;
import com.project.consolecrud.model.Post;
import com.project.consolecrud.model.PostStatus;
import com.project.consolecrud.model.Writer;
import com.project.consolecrud.utils.DBConnector;
import com.project.consolecrud.utils.SQLQuery;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepositry {
    private LabelRepository labelRepository;
    private DBConnector db;

    public PostRepositoryImpl(WriterRepository writerRepository, LabelRepository labelRepository, DBConnector db) {
        this.labelRepository = labelRepository;
        this.db = db;
    }

    private Post createPost(ResultSet rs) throws SQLException {
        Post post = new Post();
        post.setId(rs.getLong("id"));
        post.setContent(rs.getString("content"));
        post.setStatus(PostStatus.valueOf(rs.getString("status")));
        post.setCreated(rs.getDate("created"));
        post.setUpdated(rs.getDate("updated"));
        post.setLabels(labelRepository.findAllByPostId(post));
        return post;
    }

    @Override
    public void save(Post post) {
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLQuery.INSERT_POST)) {
            ps.setString(1, post.getContent());
            ps.setDate(2, post.getCreated());
            ps.executeQuery();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void savePostByWriter(Post post, Writer writer) {
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.INSERT_POST_BY_WRITER)) {
            ps.setLong(1, post.getId());
            ps.setLong(2, writer.getId());
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updatePostStatus(PostStatus status, Post post) {
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.updatePostStatus(status))) {
            ps.setLong(1 , post.getId());
            ps.executeQuery();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.selectAll("posts"))) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                posts.add(createPost(rs));
            }
            connection.commit();
            rs.close();
            return posts;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Post findById(Long id) {
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.selectById("posts"))) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            Post post = createPost(rs);
            connection.commit();
            rs.close();
            return post;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void update(Post entity) {
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.UPDATE_POST)) {
            ps.setString(1, entity.getContent());
            ps.setDate(2, Date.valueOf(LocalDate.now()));
            ps.executeQuery();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    @Override
    public void deleteById(Long id) {
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.deleteById("posts"))) {
            ps.setLong(1 ,id);
            ps.executeQuery();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Post> findAllByWriterName(Writer writer) {
        List<Post> posts = new ArrayList<>();
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.SELECT_POSTS_BY_WRITER)) {
            ps.setString(1, writer.getFirstName());
            ps.setString( 2, writer.getLastName());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                posts.add(createPost(rs));
            }

            connection.commit();
            rs.close();

            return posts;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<Label> findLabelsByPostId(Post entity) {
        return labelRepository.findAllByPostId(entity);
    }
}
