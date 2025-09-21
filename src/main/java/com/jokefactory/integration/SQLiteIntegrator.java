package com.jokefactory.integration;

import com.jokefactory.model.Joke;

import java.sql.*;

public class SQLiteIntegrator {
    private Connection conn;

    public SQLiteIntegrator(String dbFile) throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
        initSchema();
    }

    private void initSchema() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS jokes (" +
                "joke_id TEXT PRIMARY KEY," +
                "setup TEXT," +
                "punchline TEXT," +
                "source_url TEXT," +
                "length_category TEXT," +
                "keywords TEXT)";
        conn.createStatement().execute(sql);
    }

    public void insertJoke(Joke joke) throws SQLException {
        String sql = "INSERT OR REPLACE INTO jokes " +
                "(joke_id, setup, punchline, source_url, length_category, keywords) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, joke.getJokeId());
            ps.setString(2, joke.getSetup());
            ps.setString(3, joke.getPunchline());
            ps.setString(4, joke.getSourceUrl());
            ps.setString(5, joke.getLengthCategory());
            ps.setString(6, String.join(",", joke.getKeywords()));
            ps.executeUpdate();
        }
    }
}
