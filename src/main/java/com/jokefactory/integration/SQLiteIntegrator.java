package com.jokefactory.integration;

import com.jokefactory.model.Joke;

import java.sql.*;
import java.util.List;

public class SQLiteIntegrator implements AutoCloseable {
    private final Connection connection;

    public SQLiteIntegrator(String dbFile) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
        initTable();
    }

    private void initTable() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS jokes (
                joke_id TEXT PRIMARY KEY,
                setup TEXT,
                punchline TEXT,
                source_url TEXT,
                length_category TEXT,
                keywords TEXT,
                keywordsWithNLP TEXT,
                sentiment TEXT,
                sentimentWithNLP TEXT,
                readabilityScore REAL
            )
            """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void insertJoke(Joke joke) throws SQLException {
        String sql = """
            INSERT OR REPLACE INTO jokes
            (joke_id, setup, punchline, source_url, length_category, keywords, keywordsWithNLP, sentiment, sentimentWithNLP, readabilityScore)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, joke.getJokeId());
            ps.setString(2, joke.getSetup());
            ps.setString(3, joke.getPunchline());
            ps.setString(4, joke.getSourceUrl());
            ps.setString(5, joke.getLengthCategory());

            // keywords
            ps.setString(6, listToString(joke.getKeywords()));
            // keywordsWithNLP
            ps.setString(7, listToString(joke.getKeywordsWithNLP()));

            // sentiment
            ps.setString(8, joke.getSentiment());
            // sentimentWithNLP
            ps.setString(9, joke.getSentimentWithNLP());

            // readabilityScore
            ps.setObject(10, joke.getReadabilityScore());

            ps.executeUpdate();
        }
    }

    private String listToString(List<String> list) {
        return (list == null || list.isEmpty()) ? "" : String.join(",", list);
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
