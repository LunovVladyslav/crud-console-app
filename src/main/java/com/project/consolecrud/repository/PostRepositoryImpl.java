package com.project.consolecrud.repository;

import com.project.consolecrud.model.Label;
import com.project.consolecrud.model.Post;
import com.project.consolecrud.model.Writer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepositry {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private LabelRepository labelRepository;

    public PostRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Post> rowMapper = (rs, rowNum) -> {
        Post post = new Post();
        post.setId(rs.getLong("id"));
        post.setContent(rs.getString("content"));
        post.setCreated(rs.getDate("created"));
        post.setUpdated(rs.getDate("updated"));
        post.setLabels(labelRepository.findAllByPostId(post));
        return post;
    };

    @Override
    public void save(Post entity) {
        jdbcTemplate.update(
                "INSERT INTO APP.posts (id, content, created, updated) values (null, ?, ?, ?)",
                entity.getContent(),
                Date.valueOf(LocalDate.now()),
                null
        );
    }

    @Override
    public List<Post> findAll() {
        return jdbcTemplate.query("SELECT * FROM APP.posts", rowMapper);
    }

    @Override
    public Post findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM APP.posts WHERE id = ?",
                rowMapper, id);
    }

    @Override
    public void update(Post entity) {
        jdbcTemplate.update("INSERT INTO APP.posts SET content = ?, updated = ?",
                entity.getContent(), Date.valueOf(LocalDate.now()));
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM APP.posts where id = ?", id);
    }

    @Override
    public List<Post> findAllByWriterName(Writer writer) {
        return jdbcTemplate.query("SELECT * FROM APP.posts " +
                        "JOIN APP.post_writer pw on posts.id = pw.post_id " +
                        "JOIN APP.writers w on w.id = pw.writer_id " +
                        "WHERE `first-name` = ? and  `last-name` = ?",
                rowMapper, writer.getFirstName(), writer.getLastName());
    }

    @Override
    public List<Label> findLabelsByPostId(Post entity) {
        return labelRepository.findAllByPostId(entity);
    }
}
