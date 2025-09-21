package com.jokefactory.impl;

import com.jokefactory.decorator.JokeDecorator;
import com.jokefactory.model.Joke;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;

import java.util.Properties;

public class SentimentAnalysisDecoratorWithNLP implements JokeDecorator {
    private final StanfordCoreNLP pipeline;

    public SentimentAnalysisDecoratorWithNLP() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,parse,sentiment");
        this.pipeline = new StanfordCoreNLP(props);
    }

    @Override
    public void enrich(Joke joke) {
        String text = joke.getSetup() + " " + joke.getPunchline();

        Annotation annotation = new Annotation(text);
        pipeline.annotate(annotation);

        String sentimentResult = "neutral"; // 默认值
        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            sentimentResult = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
            // 这里简单用最后一句话的情感，可以改成取平均或最大值
        }
        System.out.println("Sentiment: " + sentimentResult);

        joke.setSentimentWithNLP(sentimentResult);
    }
}
