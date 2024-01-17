package com.project.consolecrud.utils;

import com.project.consolecrud.model.Label;
import com.project.consolecrud.model.Post;
import com.project.consolecrud.model.PostStatus;
import com.project.consolecrud.model.Writer;
import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class MapperTest {
    public static final String FIRST_NAME = "name";
    public static final String LAST_NAME = "lastName";
    public static final String CONTENT = "someContent";
    public static final Long ID = 1L;

    @NotNull
    public static Writer createWriter() {
        Writer writer = new Writer();
        writer.setId(ID);
        writer.setFirstName(FIRST_NAME);
        writer.setLastName(LAST_NAME);
        return writer;
    }

    @NotNull
    public static Post createPost() {
        Post post = new Post();
        post.setId(ID);
        post.setContent(CONTENT);
        post.setStatus(PostStatus.ACTIVE);
        post.setLabels(List.of(createLabel()));
        post.setCreated(Date.valueOf(LocalDate.now()));
        return post;
    }

    @NotNull
    public static Label createLabel() {
        Label label = new Label();
        label.setId(ID);
        label.setName(FIRST_NAME);
        return label;
    }

}
