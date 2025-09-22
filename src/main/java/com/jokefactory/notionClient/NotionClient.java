package com.jokefactory.notionClient;

import com.jokefactory.model.Joke;
import okhttp3.*;

public class NotionClient {
    private static final String API_KEY = "secret_xxx";
    private static final String DATABASE_ID = "xxx";

    private static final OkHttpClient client = new OkHttpClient();

    public static void saveJoke(Joke joke) throws Exception {
        String json = "{"
                + "\"parent\": {\"database_id\": \"" + DATABASE_ID + "\"},"
                + "\"properties\": {"
                + "  \"JokeId\": {\"title\": [{\"text\": {\"content\": \"" + joke.getJokeId() + "\"}}]},"
                + "  \"Setup\": {\"rich_text\": [{\"text\": {\"content\": \"" + joke.getSetup() + "\"}}]},"
                + "  \"Punchline\": {\"rich_text\": [{\"text\": {\"content\": \"" + joke.getPunchline() + "\"}}]}"
                + "}}";

        Request request = new Request.Builder()
                .url("https://api.notion.com/v1/pages")
                .post(RequestBody.create(json, MediaType.parse("application/json")))
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Notion-Version", "2022-06-28")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }
}
