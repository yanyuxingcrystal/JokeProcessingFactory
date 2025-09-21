package com.jokefactory.decorator;

import com.jokefactory.model.Joke;

public interface JokeDecorator {
    void enrich(Joke joke);
}
