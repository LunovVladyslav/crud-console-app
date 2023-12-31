package com.project.consolecrud.repository;

import com.project.consolecrud.model.Label;
import com.project.consolecrud.model.Post;
import com.project.consolecrud.model.PostStatus;
import com.project.consolecrud.model.Writer;
import com.project.consolecrud.utils.DBConnector;
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
    private SQLQuery sqlQuery;

    public PostRepositoryImpl(LabelRepository labelRepository, DBConnector db, SQLQuery sqlQuery) {
        this.labelRepository = labelRepository;
        this.db = db;
        this.sqlQuery = sqlQuery;
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
             PreparedStatement ps = connection.prepareStatement(sqlQuery.INSERT_POST)) {
            ps.setString(1, post.getContent());
            ps.setString(2, post.getStatus().getValue());
            ps.setDate(3, post.getCreated());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void savePostByWriter(Post post, Writer writer) {
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlQuery.INSERT_POST_BY_WRITER)) {
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
            PreparedStatement ps = connection.prepareStatement(sqlQuery.updatePostStatus(status))) {

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
            PreparedStatement ps = connection.prepareStatement(sqlQuery.selectAll("posts"));
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                posts.add(createPost(rs));
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
            PreparedStatement ps = connection.prepareStatement(sqlQuery.selectById("posts"))) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    post = createPost(rs);
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
    public void update(Post entity) {
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlQuery.UPDATE_POST)) {
            ps.setString(1, entity.getContent());
            ps.setDate(2, Date.valueOf(LocalDate.now()));
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    @Override
    public void deleteById(Long id) {
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlQuery.deleteById("posts"))) {
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
            PreparedStatement ps = connection.prepareStatement(sqlQuery.SELECT_POSTS_BY_WRITER)) {

            ps.setString(1, writer.getFirstName());
            ps.setString( 2, writer.getLastName());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    posts.add(createPost(rs));
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
            PreparedStatement ps = connection.prepareStatement(sqlQuery.SELECT_POST_BY_CONTENT)) {
            ps.setString(1, content);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    post = createPost(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  post;
    }

    @Override
    public List<Label> findLabelsByPostId(Post entity) {
        return labelRepository.findAllByPostId(entity);
    }

    @Override
    public List<Post> findPostByLabelId(Label label) {
        List<Post> postList = new ArrayList<>();
        try (Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlQuery.SELECT_POSTS_BY_LABEL)) {
            ps.setLong(1, label.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    postList.add(createPost(rs));
                }
            }
            connection.commit();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return postList;
    }
}
