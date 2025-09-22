package com.jokefactory.pipeline;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.jokefactory.decorator.JokeDecorator;
import com.jokefactory.integration.SQLiteIntegrator;
import com.jokefactory.model.Joke;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class JokeProcessor {
    private final List<JokeDecorator> decorators;
    private final ExecutorService executor;
    private final BlockingQueue<Joke> queue;
    private final ObjectMapper mapper;

    public JokeProcessor(List<JokeDecorator> decorators) {
        this.decorators = decorators;
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        this.queue = new LinkedBlockingQueue<>();
        this.mapper = new ObjectMapper();
    }

    public void processFile(String inputFile, String enrichedFile, String dbFile) throws Exception {
        ObjectReader reader = mapper.readerFor(Joke.class);

        // 1. Load jokes
        List<Joke> jokes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                jokes.add(reader.readValue(line));
            }
        }

        // 2. Start DB writer thread
        Thread dbWriter = new Thread(() -> {
            try (SQLiteIntegrator integrator = new SQLiteIntegrator(dbFile)) {
                while (true) {
                    Joke joke = queue.poll(5, TimeUnit.SECONDS);
                    if (joke == null) break; // exit if idle
                    integrator.insertJoke(joke);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        dbWriter.start();

        // 3. Process jokes in parallel
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(enrichedFile))) {
            List<Future<?>> futures = new ArrayList<>();
            for (Joke joke : jokes) {
                futures.add(executor.submit(() -> {
                    try {
                        // run decorators
                        for (JokeDecorator decorator : decorators) {
                            decorator.enrich(joke);
                        }
                        synchronized (bw) {
                            bw.write(mapper.writeValueAsString(joke));
                            bw.newLine();
                        }
                        queue.offer(joke); // send to DB writer
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }));
            }

            // wait for all tasks
            for (Future<?> f : futures) f.get();
        }

        // 4. shutdown
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);
        dbWriter.join();
    }
}