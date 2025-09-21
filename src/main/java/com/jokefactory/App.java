package com.jokefactory;

import com.jokefactory.decorator.*;
import com.jokefactory.impl.KeywordExtractorDecorator;
import com.jokefactory.impl.LengthClassifierDecorator;
import com.jokefactory.integration.SQLiteIntegrator;
import com.jokefactory.model.Joke;
import com.jokefactory.pipeline.JokeProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import java.io.*;
import java.sql.SQLException;
import java.util.Arrays;

public class App {
    public static void main(String[] args) throws Exception {
        String inputFile = "src/main/resources/jokes.jsonl";
        String enrichedFile = "src/main/resources/enriched_jokes.jsonl";
        String dbFile = "jokes.db";

        // Step 1: 处理数据
        JokeProcessor processor = new JokeProcessor(Arrays.asList(
                new LengthClassifierDecorator(),
                new KeywordExtractorDecorator()
        ));
        processor.processFile(inputFile, enrichedFile);

        // Step 2: 写入数据库
        SQLiteIntegrator integrator = new SQLiteIntegrator(dbFile);
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.readerFor(Joke.class);

        try (BufferedReader br = new BufferedReader(new FileReader(enrichedFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                Joke joke = reader.readValue(line);
                integrator.insertJoke(joke);
            }
        }

        System.out.println("✅ 数据处理并成功写入 SQLite 数据库");
    }
}
