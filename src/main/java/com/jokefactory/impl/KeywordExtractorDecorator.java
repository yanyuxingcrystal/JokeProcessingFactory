package com.jokefactory.impl;

import com.jokefactory.decorator.JokeDecorator;
import com.jokefactory.model.Joke;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KeywordExtractorDecorator implements JokeDecorator {
    private static final List<String> PROGRAMMING_KEYWORDS = Arrays.asList(
            "java", "python", "bug", "debug", "stack", "memory", "null", "exception", "thread"
    );

    @Override
    public void enrich(Joke joke) {
        String text = (joke.getSetup() + " " + joke.getPunchline()).toLowerCase();
        List<String> found = new ArrayList<>();
        for (String keyword : PROGRAMMING_KEYWORDS) {
            if (text.contains(keyword)) {
                found.add(keyword);
            }
        }
        joke.setKeywords(found);
    }
}