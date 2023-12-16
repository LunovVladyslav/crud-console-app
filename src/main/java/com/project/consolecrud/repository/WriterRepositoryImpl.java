package com.project.consolecrud.repository;

import com.project.consolecrud.model.Writer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WriterRepositoryImpl implements WriterRepository{
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PostRepositry postRepositry;


    public WriterRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Writer> rowMapper = (rs, rowNum) -> {
        Writer writer = new Writer();
        writer.setId(rs.getLong("id"));
        writer.setFirstName(rs.getString("first_name"));
        writer.setLastName(rs.getString("last_name"));
        writer.setPosts(postRepositry.findAllByWriterName(writer));
        return writer;
    };


    @Override
    public void save(Writer entity) {
        jdbcTemplate.update(
                "INSERT INTO APP.writers (id, first_name, last_name) values (null, ?, ?)",
                entity.getFirstName(),
                entity.getLastName()
        );
    }

    @Override
    public List<Writer> findAll() {
        return jdbcTemplate.query("SELECT * FROM APP.writers", rowMapper);
    }

    @Override
    public Writer findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM APP.writers WHERE id = ?",
                rowMapper, id);
    }

    @Override
    public void update(Writer entity) {
         jdbcTemplate.update(
                 "UPDATE APP.writers SET first_name = ?, last_name = ? WHERE id = ?",
                 entity.getFirstName(), entity.getLastName(), entity.getId()
         );
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM APP.writers WHERE id = ?", id);
    }

    @Override
    public Writer findByName(String firstName, String lastName) {
        return jdbcTemplate.queryForObject("SELECT * FROM APP.writers WHERE first_name = ?, last_name = ?",
                rowMapper, firstName, lastName);
    }
}
