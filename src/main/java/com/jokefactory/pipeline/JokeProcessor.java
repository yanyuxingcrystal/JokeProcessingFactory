package com.jokefactory.pipeline;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.jokefactory.decorator.JokeDecorator;
import com.jokefactory.model.Joke;

import java.io.*;
import java.util.List;

public class JokeProcessor {
    private final List<JokeDecorator> decorators;

    public JokeProcessor(List<JokeDecorator> decorators) {
        this.decorators = decorators;
    }

    public void processFile(String inputPath, String outputPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.readerFor(Joke.class);

        try (BufferedReader br = new BufferedReader(new FileReader(inputPath));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath))) {

            String line;
            while ((line = br.readLine()) != null) {
                Joke joke = reader.readValue(line);

                // 应用所有装饰器
                for (JokeDecorator d : decorators) {
                    d.enrich(joke);
                }

                bw.write(mapper.writeValueAsString(joke));
                bw.newLine();
            }
        }
    }
}