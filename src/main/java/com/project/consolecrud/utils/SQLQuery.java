package com.project.consolecrud.utils;

import org.jetbrains.annotations.NotNull;

public class SQLQuery {
//    For writers
    public static final String INSERT_WRITER = "INSERT INTO APP.writers (first_name, last_name) VALUES (?, ?)";
    public static final String UPDATE_WRITER = "UPDATE APP.writers SET first_name = ?, last_name = ? WHERE id = ?";
    public static final String SELECT_WRITER_BY_NAME = "SELECT * FROM APP.writers WHERE first_name = ? AND last_name = ?";

//    For posts
    public static final  String INSERT_POST = "INSERT INTO APP.posts (id, content, created, updated) values (null, ?, ?, ?)";


    public static String selectAll(@NotNull String table) {
        return String.format("SELECT * FROM APP.%s", table.toLowerCase());
    }

    public static String selectById(@NotNull String table) {
        return String.format("SELECT * FROM APP.%s WHERE id = ?", table.toLowerCase());
    }

    public static String deleteById(@NotNull String table) {
        return String.format("DELETE FROM APP.%s WHERE id = ?", table.toLowerCase());
    }
 }
