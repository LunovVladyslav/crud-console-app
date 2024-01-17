package com.project.consolecrud.repository;

import com.project.consolecrud.model.Label;
import com.project.consolecrud.model.Post;
import com.project.consolecrud.model.PostStatus;
import com.project.consolecrud.model.Writer;
import com.project.consolecrud.utils.DBConnector;
import com.project.consolecrud.utils.Mapper;
import com.project.consolecrud.utils.SQLQuery;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepositry {
    private LabelRepository labelRepository;
    private DBConnector db;
    private Mapper mapper;


    public PostRepositoryImpl(LabelRepository labelRepository, DBConnector db, Mapper mapper) {
        this.labelRepository = labelRepository;
        this.db = db;
        this.mapper = mapper;
    }

    @Override
    public Post save(Post post) {
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLQuery.INSERT_POST)) {
            ps.setString(1, post.getContent());
            ps.setString(2, post.getStatus().getValue());
            ps.setDate(3, post.getCreated());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Post res = findPostByContent(post.getContent());
        res.setLabels(labelRepository.findAllByPostId(post));
        return res;
    }

    @Override
    public void savePostByWriter(Post post, Writer writer) {
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.INSERT_POST_BY_WRITER)) {
            ps.setLong(1, post.getId());
            ps.setLong(2, writer.getId());
            ps.executeUpdate();
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
            ps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.selectAll("posts"));
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                posts.add(mapper.createPost(rs));
            }
            connection.commit();
            return posts;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Post findById(Long id) {
        Post post = null;
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.selectById("posts"))) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    post = mapper.createPost(rs);
                    post.setLabels(labelRepository.findAllByPostId(post));
                }
            }

            connection.commit();
            return post;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return post;
        }
    }



    @Override
    public Post update(Post entity) {
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.UPDATE_POST)) {
            ps.setString(1, entity.getContent());
            ps.setDate(2, Date.valueOf(LocalDate.now()));
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Post post = findById(entity.getId());
        post.setLabels(labelRepository.findAllByPostId(post));
        return post;
    }



    @Override
    public void deleteById(Long id) {
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.deleteById("posts"))) {
            ps.setLong(1 ,id);
            ps.executeUpdate();
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

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    posts.add(mapper.createPost(rs));
                }
            }

            connection.commit();
            return posts;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Post findPostByContent(String content) {
        Post post = null;
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.SELECT_POST_BY_CONTENT)) {
            ps.setString(1, content);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    post = mapper.createPost(rs);
                    post.setLabels(labelRepository.findAllByPostId(post));
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return post;
    }

    @Override
    public List<Label> findLabelsByPostId(Post entity) {
        return labelRepository.findAllByPostId(entity);
    }

    @Override
    public List<Post> findPostByLabelId(Label label) {
        List<Post> postList = new ArrayList<>();
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQLQuery.SELECT_POSTS_BY_LABEL)) {
            ps.setLong(1, label.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Post post = mapper.createPost(rs);
                    post.setLabels(labelRepository.findAllByPostId(post));
                    postList.add(post);
                }
            }
            connection.commit();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return postList;
    }
}
