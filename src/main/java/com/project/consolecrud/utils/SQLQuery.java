package com.project.consolecrud.utils;

import com.project.consolecrud.model.PostStatus;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class SQLQuery {

    @Value("${application.database}")
    private static String dbName = "APPLICATION";

    //    For writers
    public static final String INSERT_WRITER =
        "INSERT INTO %s.writers (first_name, last_name) VALUES (?, ?)".formatted(dbName);
    public static final String UPDATE_WRITER =
            "UPDATE %s.writers SET first_name = ?, last_name = ? WHERE id = ?".formatted(dbName);
    public  static final String SELECT_WRITER_BY_NAME =
            "SELECT * FROM %s.writers WHERE first_name = ? AND last_name = ?".formatted(dbName);

    public static final String SELECT_WRITER_BY_POST = "SELECT * FROM %s.writers ".formatted(dbName) +
                    "JOIN %s.post_writer pw on writers.id = pw.writer_id ".formatted(dbName) +
                    "JOIN %s.posts p on p.id = pw.post_id ".formatted(dbName) +
                    "WHERE p.id = ?";

//    For posts
    public static final String SELECT_POST_BY_CONTENT =
        "SELECT id, content, status, created, updated FROM %s.posts WHERE content LIKE ?".formatted(dbName);
    public static final String INSERT_POST =
        "INSERT INTO %s.posts (id, content, status, created, updated) values (null, ?, ?, ?, null)".formatted(dbName);
    public static final String UPDATE_POST =
            "INSERT INTO %s.posts SET content = ?, updated = ? WHERE id = ?".formatted(dbName);
    public static final String SELECT_POSTS_BY_WRITER = "SELECT * FROM %s.posts ".formatted(dbName) +
                        "JOIN %s.post_writer pw on posts.id = pw.post_id ".formatted(dbName) +
                        "JOIN %s.writers w on w.id = pw.writer_id ".formatted(dbName) +
                        "WHERE w.first_name = ? and w.last_name = ?";

    public static final String SELECT_POSTS_BY_LABEL = "SELECT * FROM %s.posts ".formatted(dbName) +
            "JOIN %s.post_label pl on posts.id = pl.post_id ".formatted(dbName) +
            "JOIN %s.labels l on l.id = pl.label_id ".formatted(dbName) +
            "WHERE l.id = ?";

//    For post_writer
    public static final String INSERT_POST_BY_WRITER =
        "INSERT INTO %s.post_writer (post_id, writer_id) VALUES (?, ?)".formatted(dbName);

//    For label
    public static final String SELECT_LABEL_BY_NAME =
            "SELECT id, name FROM %s.labels WHERE name like ?".formatted(dbName);
    public static final String SELECT_LABELS_BY_POST = "SELECT * FROM %s.labels ".formatted(dbName) +
                "JOIN %s.post_label pl on labels.id = pl.label_id ".formatted(dbName) +
                "JOIN %s.posts p on p.id = pl.post_id ".formatted(dbName) +
                "WHERE p.id = ?";
    public static final String INSERT_LABEL =
        "INSERT INTO %s.labels (name) VALUES (?)".formatted(dbName);
    public static final String INSERT_LABEL_BY_POST =
            "INSERT INTO post_label (post_id, label_id) VALUES (?, ?)";
    public static final String UPDATE_LABEL =
            "INSERT INTO %s.labels SET name = ? WHERE id = ?".formatted(dbName);


    //    methods
    public static String selectAll(@NotNull String table) {
        return String.format("SELECT * FROM %s.%s", dbName, table.toLowerCase());
    }

    public static String selectById(@NotNull String table) {
        return String.format("SELECT * FROM %s.%s WHERE id = ?", dbName, table.toLowerCase());
    }

    public static String updatePostStatus(@NotNull PostStatus status) {
        return String.format("INSERT INTO %s.posts SET status = %s WHERE id = ?", dbName, status.getValue());
    }

    public static String deleteById(@NotNull String table) {
        return String.format("DELETE FROM %s.%s WHERE id = ?", dbName, table.toLowerCase());
    }

 }
