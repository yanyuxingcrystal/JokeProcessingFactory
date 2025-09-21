package com.jokefactory.impl;

import com.jokefactory.decorator.JokeDecorator;
import com.jokefactory.model.Joke;

public class ReadabilityScoreDecorator implements JokeDecorator {
    @Override
    public void enrich(Joke joke) {
        String text = joke.getSetup() + " " + joke.getPunchline();

        int sentenceCount = text.split("[.!?]").length;
        String[] words = text.split("\\s+");
        int wordCount = words.length;

        int syllableCount = 0;
        for (String word : words) {
            syllableCount += countSyllables(word);
        }

        double fleschReadingEase = 206.835
                - 1.015 * ((double) wordCount / sentenceCount)
                - 84.6 * ((double) syllableCount / wordCount);

        // 加一个新字段到 Joke.java
        joke.setReadabilityScore(fleschReadingEase);
    }

    private int countSyllables(String word) {
        String vowels = "aeiouy";
        word = word.toLowerCase().replaceAll("[^a-z]", "");
        boolean prevVowel = false;
        int count = 0;

        for (char c : word.toCharArray()) {
            boolean isVowel = vowels.indexOf(c) != -1;
            if (isVowel && !prevVowel) {
                count++;
            }
            prevVowel = isVowel;
        }

        if (word.endsWith("e")) {
            count = Math.max(1, count - 1);
        }

        return Math.max(count, 1);
    }
}