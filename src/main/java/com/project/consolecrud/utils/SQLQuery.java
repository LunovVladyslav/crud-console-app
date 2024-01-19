package com.project.consolecrud.utils;

import com.project.consolecrud.model.PostStatus;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class SQLQuery {

//    @Value("${application.database}")
//    private static String dbName = "APPLICATION";

    //    For writers
    public static final String INSERT_WRITER =
        "INSERT INTO writers (first_name, last_name) VALUES (?, ?)";
    public static final String UPDATE_WRITER =
            "UPDATE writers SET first_name = ?, last_name = ? WHERE id = ?";
    public  static final String SELECT_WRITER_BY_NAME =
            "SELECT * FROM writers WHERE first_name = ? AND last_name = ?";

    public static final String SELECT_WRITER_BY_POST = "SELECT * FROM writers " +
                    "JOIN post_writer pw on writers.id = pw.writer_id " +
                    "JOIN posts p on p.id = pw.post_id " +
                    "WHERE p.id = ?";

//    For posts
    public static final String SELECT_POST_BY_CONTENT =
        "SELECT id, content, status, created, updated FROM posts WHERE content LIKE ?";
    public static final String INSERT_POST =
        "INSERT INTO posts (id, content, status, created, updated) values (null, ?, ?, ?, null)";
    public static final String UPDATE_POST =
            "INSERT INTO posts SET content = ?, updated = ? WHERE id = ?";
    public static final String SELECT_POSTS_BY_WRITER = "SELECT * FROM posts " +
                        "JOIN post_writer pw on posts.id = pw.post_id " +
                        "JOIN writers w on w.id = pw.writer_id " +
                        "WHERE w.first_name = ? and w.last_name = ?";

    public static final String SELECT_POSTS_BY_LABEL = "SELECT * FROM posts " +
            "JOIN post_label pl on posts.id = pl.post_id " +
            "JOIN labels l on l.id = pl.label_id " +
            "WHERE l.id = ?";

//    For post_writer
    public static final String INSERT_POST_BY_WRITER =
        "INSERT INTO post_writer (post_id, writer_id) VALUES (?, ?)";

//    For label
    public static final String SELECT_LABEL_BY_NAME =
            "SELECT id, name FROM %s.labels WHERE name like ?";
    public static final String SELECT_LABELS_BY_POST = "SELECT * FROM labels " +
                "JOIN post_label pl on labels.id = pl.label_id " +
                "JOIN posts p on p.id = pl.post_id " +
                "WHERE p.id = ?";
    public static final String INSERT_LABEL =
        "INSERT INTO labels (name) VALUES (?)";
    public static final String INSERT_LABEL_BY_POST =
            "INSERT INTO post_label (post_id, label_id) VALUES (?, ?)";
    public static final String UPDATE_LABEL =
            "INSERT INTO labels SET name = ? WHERE id = ?";


    //    methods
    public static String selectAll(@NotNull String table) {
        return String.format("SELECT * FROM %s", table.toLowerCase());
    }

    public static String selectById(@NotNull String table) {
        return String.format("SELECT * FROM %s WHERE id = ?", table.toLowerCase());
    }

    public static String updatePostStatus(@NotNull PostStatus status) {
        return String.format("INSERT INTO posts SET status = %s WHERE id = ?", status.getValue());
    }

    public static String deleteById(@NotNull String table) {
        return String.format("DELETE FROM %s WHERE id = ?", table.toLowerCase());
    }

 }
