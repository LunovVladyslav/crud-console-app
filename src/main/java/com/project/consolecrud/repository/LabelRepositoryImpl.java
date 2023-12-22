package com.project.consolecrud.repository;

import com.project.consolecrud.model.Label;
import com.project.consolecrud.model.Post;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LabelRepositoryImpl implements LabelRepository{

    public LabelRepositoryImpl() {
    }

    @Override
    public void save(Label entity) {

    }

    @Override
    public List<Label> findAll() {
        return null;
    }

    @Override
    public Label findById(Long aLong) {
        return null;
    }

    @Override
    public List<Label> findAllByPostId(Post entity) {
        return null;
    }

    @Override
    public void update(Label entity) {

    }

    @Override
    public void deleteById(Long aLong) {

    }


}
