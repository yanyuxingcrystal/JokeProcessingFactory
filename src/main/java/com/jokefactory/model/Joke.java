package com.jokefactory.model;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Joke {
    private String jokeId;
    private String setup;
    private String punchline;
    private String sourceUrl;

    // 增强后的字段
    private String lengthCategory; // short / medium / long
    private List<String> keywords;
    private List<String> keywordsWithNLP;
    private List<String> keywordsWithAIPrompt;
    private String sentiment;
    private String sentimentWithNLP;

    // Getters/Setters
    @JsonProperty("joke_id")
    public String getJokeId() { return jokeId; }
    public void setJokeId(String jokeId) { this.jokeId = jokeId; }

    public String getSetup() { return setup; }
    public void setSetup(String setup) { this.setup = setup; }

    public String getPunchline() { return punchline; }
    public void setPunchline(String punchline) { this.punchline = punchline; }

    @JsonProperty("source_url")
    public String getSourceUrl() { return sourceUrl; }
    public void setSourceUrl(String sourceUrl) { this.sourceUrl = sourceUrl; }

    public String getLengthCategory() { return lengthCategory; }
    public void setLengthCategory(String lengthCategory) { this.lengthCategory = lengthCategory; }

    public List<String> getKeywords() { return keywords; }
    public void setKeywords(List<String> keywords) { this.keywords = keywords; }

    public List<String> getKeywordsWithNLP() { return keywordsWithNLP; }
    public void setKeywordsWithNLP(List<String> keywordsWithNLP) { this.keywordsWithNLP = keywordsWithNLP; }

    public List<String> getKeywordsWithAIPrompt() { return keywordsWithAIPrompt; }
    public void setKeywordsWithAIPrompt(List<String> keywordsWithAIPrompt) { this.keywordsWithAIPrompt = keywordsWithAIPrompt; }

    public String getSentiment() { return sentiment; }
    public void setSentiment(String sentiment) { this.sentiment = sentiment; }
    public String getSentimentWithNLP() { return sentimentWithNLP; }
    public void setSentimentWithNLP(String sentimentWithNLP) { this.sentimentWithNLP = sentimentWithNLP; }

    private Double readabilityScore;

    public Double getReadabilityScore() { return readabilityScore; }
    public void setReadabilityScore(Double readabilityScore) { this.readabilityScore = readabilityScore; }

    @Override
    public String toString() {
        return "Joke{" +
                "id='" + jokeId + '\'' +
                ", setup='" + setup + '\'' +
                ", punchline='" + punchline + '\'' +
                ", lengthCategory='" + lengthCategory + '\'' +
                ", keywords=" + keywords +
                '}';
    }
}
