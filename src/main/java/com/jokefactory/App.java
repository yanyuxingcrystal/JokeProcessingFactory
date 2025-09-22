package com.jokefactory;

import com.jokefactory.decorator.*;
import com.jokefactory.impl.*;
import com.jokefactory.pipeline.JokeProcessor;

import java.io.*;
import java.util.Arrays;

public class App {
    public static void main(String[] args) throws Exception {
        String inputFile = "src/main/resources/ProgrammingJokes.jsonl";
        String enrichedFile = "src/main/resources/enriched_jokes.jsonl";
        String dbFile = "jokes.db";

        // Step 1: 处理数据
        JokeProcessor processor = new JokeProcessor(Arrays.asList(
                new LengthClassifierDecorator(),
                new KeywordExtractorDecorator(),
                new KeywordExtractorDecoratorWithNLP(),
                new SentimentAnalysisDecorator(),
                new SentimentAnalysisDecoratorWithNLP(),
                new ReadabilityScoreDecorator()

        ));

        processor.processFile(inputFile,
                enrichedFile,
                dbFile);

        System.out.println("✅ Data processing successfully and put into SQLite ");
    }
}
