package com.jokefactory.impl;

import com.jokefactory.decorator.JokeDecorator;
import com.jokefactory.model.Joke;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class AIKeywordExtractorDecorator implements JokeDecorator {
    private final OkHttpClient client = new OkHttpClient();
    private final String apiKey;
    private final ObjectMapper mapper = new ObjectMapper();

    public AIKeywordExtractorDecorator(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public void enrich(Joke joke) {
        try {
            String prompt = "Extract key programming concepts from this joke as a list of keywords:\n\n"
                    + joke.getSetup() + " " + joke.getPunchline();

            String response = callOpenAI(prompt);
            List<String> keywords = parseKeywords(response);
            joke.setKeywordsWithAIPrompt(keywords);
        } catch (Exception e) {
            joke.setKeywordsWithAIPrompt(List.of("AI_Failed"));
        }
    }

    private String callOpenAI(String prompt) throws IOException {
        ObjectNode json = mapper.createObjectNode();
        json.put("model", "gpt-4o-mini");

        ArrayNode messages = mapper.createArrayNode();
        ObjectNode message = mapper.createObjectNode();
        message.put("role", "user");
        message.put("content", prompt);
        messages.add(message);

        json.set("messages", messages);
        json.put("temperature", 0);

        RequestBody body = RequestBody.create(
                mapper.writeValueAsString(json),
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    private List<String> parseKeywords(String response) {
        List<String> keywords = new ArrayList<>();
        try {
            JsonNode root = mapper.readTree(response);
            String content = root.path("choices").get(0).path("message").path("content").asText();

            for (String kw : content.split(",")) {
                keywords.add(kw.trim());
            }
        } catch (Exception e) {
            keywords.add("ParseError");
        }
        return keywords;
    }
}