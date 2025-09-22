package com.jokefactory.controller;

import com.jokefactory.model.Joke;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.*;

@RestController
@RequestMapping("/jokes")
public class JokeController {
    private final String dbFile = "jokes.db";

    // 查询所有笑话
    @GetMapping
    public List<Joke> getAllJokes() throws SQLException {
        List<Joke> jokes = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile)) {
            String sql = "SELECT * FROM jokes";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()) {
                Joke joke = new Joke();
                joke.setJokeId(rs.getString("joke_id"));
                joke.setSetup(rs.getString("setup"));
                joke.setPunchline(rs.getString("punchline"));
                joke.setSourceUrl(rs.getString("source_url"));
                joke.setLengthCategory(rs.getString("length_category"));
                joke.setKeywords(Arrays.asList(rs.getString("keywords").split(",")));
                joke.setKeywordsWithNLP(Arrays.asList(rs.getString("keywordsWithNLP").split(",")));
                joke.setSentimentWithNLP(rs.getString("sentimentWithNLP"));
                joke.setReadabilityScore(rs.getDouble("readabilityScore"));
                jokes.add(joke);
            }
        }
        return jokes;
    }

    // 根据 ID 查询笑话
    @GetMapping("/{id}")
    public Joke getJokeById(@PathVariable String id) throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile)) {
            String sql = "SELECT * FROM jokes WHERE joke_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Joke joke = new Joke();
                joke.setJokeId(rs.getString("joke_id"));
                joke.setSetup(rs.getString("setup"));
                joke.setPunchline(rs.getString("punchline"));
                joke.setSourceUrl(rs.getString("source_url"));
                joke.setLengthCategory(rs.getString("length_category"));
                joke.setKeywords(Arrays.asList(rs.getString("keywords").split(",")));
                joke.setKeywordsWithNLP(Arrays.asList(rs.getString("keywordsWithNLP").split(",")));
                joke.setSentimentWithNLP(rs.getString("sentimentWithNLP"));
                joke.setReadabilityScore(rs.getDouble("readabilityScore"));
                return joke;
            }
        }
        return null;
    }
}