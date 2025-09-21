package com.jokefactory.impl;

import com.jokefactory.decorator.JokeDecorator;
import com.jokefactory.model.Joke;

import java.util.Arrays;
import java.util.List;

public class SentimentAnalysisDecorator implements JokeDecorator {
    private static final List<String> POSITIVE_WORDS = Arrays.asList(
            "funny", "great", "love", "happy", "awesome", "joy", "cool", "lol"
    );

    private static final List<String> NEGATIVE_WORDS = Arrays.asList(
            "boring", "bad", "stupid", "hate", "fail", "dumb", "annoying"
    );

    @Override
    public void enrich(Joke joke) {
        String text = (joke.getSetup() + " " + joke.getPunchline()).toLowerCase();

        long posCount = POSITIVE_WORDS.stream().filter(text::contains).count();
        long negCount = NEGATIVE_WORDS.stream().filter(text::contains).count();

        String sentiment;
        if (posCount > negCount) {
            sentiment = "positive";
        } else if (negCount > posCount) {
            sentiment = "negative";
        } else {
            sentiment = "neutral";
        }

        // 假设我们在 Joke 类里加一个 sentiment 字段
        joke.setSentiment(sentiment);
    }
}
