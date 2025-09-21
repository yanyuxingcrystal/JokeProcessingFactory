package com.jokefactory.impl;

import com.jokefactory.decorator.JokeDecorator;
import com.jokefactory.model.Joke;

public class LengthClassifierDecorator implements JokeDecorator {
    @Override
    public void enrich(Joke joke) {
        String text = joke.getSetup() + " " + joke.getPunchline();
        int wordCount = text.split("\\s+").length;

        if (wordCount < 10) {
            joke.setLengthCategory("short");
        } else if (wordCount < 20) {
            joke.setLengthCategory("medium");
        } else {
            joke.setLengthCategory("long");
        }
    }
}
