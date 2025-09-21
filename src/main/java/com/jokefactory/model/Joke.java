package com.jokefactory.model;
import java.util.List;

public class Joke {
    private String jokeId;
    private String setup;
    private String punchline;
    private String sourceUrl;

    // 增强后的字段
    private String lengthCategory; // short / medium / long
    private List<String> keywords;

    // Getters/Setters
    public String getJokeId() { return jokeId; }
    public void setJokeId(String jokeId) { this.jokeId = jokeId; }

    public String getSetup() { return setup; }
    public void setSetup(String setup) { this.setup = setup; }

    public String getPunchline() { return punchline; }
    public void setPunchline(String punchline) { this.punchline = punchline; }

    public String getSourceUrl() { return sourceUrl; }
    public void setSourceUrl(String sourceUrl) { this.sourceUrl = sourceUrl; }

    public String getLengthCategory() { return lengthCategory; }
    public void setLengthCategory(String lengthCategory) { this.lengthCategory = lengthCategory; }

    public List<String> getKeywords() { return keywords; }
    public void setKeywords(List<String> keywords) { this.keywords = keywords; }

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
