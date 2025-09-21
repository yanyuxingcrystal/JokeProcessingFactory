package com.jokefactory.impl;

import com.jokefactory.decorator.JokeDecorator;
import com.jokefactory.model.Joke;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.CoreMap;

import java.util.*;

public class KeywordExtractorDecoratorWithNLP implements JokeDecorator {
    private final StanfordCoreNLP pipeline;

    // 常见停用词（可以扩展）
    private static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList(
            "the", "a", "an", "is", "are", "was", "were", "to", "and", "or", "but",
            "if", "on", "in", "at", "of", "for", "by", "with", "about", "as", "it"
    ));

    public KeywordExtractorDecoratorWithNLP() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos");
        this.pipeline = new StanfordCoreNLP(props);
    }

    @Override
    public void enrich(Joke joke) {
        String text = joke.getSetup() + " " + joke.getPunchline();

        Annotation document = new Annotation(text);
        pipeline.annotate(document);

        List<String> keywords = new ArrayList<>();

        for (CoreMap sentence : document.get(CoreAnnotations.SentencesAnnotation.class)) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.word().toLowerCase();
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);

                // 只取名词，过滤掉停用词和长度过短的词
                if (pos.startsWith("NN") && word.length() > 1 && !STOP_WORDS.contains(word)) {
                    keywords.add(word);
                }
            }
        }

        joke.setKeywordsWithNLP(keywords);
    }
}
