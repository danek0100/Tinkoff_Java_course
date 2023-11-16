package edu.hw6;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides methods to interact with the Hacker News API.
 */
public class HackerNews {

    private final HttpClient httpClient;

    /**
     * Retrieves an array of IDs for the top stories on Hacker News.
     *
     * @return An array of IDs representing the top stories.
     *         In case of an error, returns an empty array.
     */
    public long[] hackerNewsTopStories() {
        String url = "https://hacker-news.firebaseio.com/v0/topstories.json";
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            return parseJsonArrayToLongs(body);
        } catch (IOException | InterruptedException e) {
            return new long[0];
        }
    }

    /**
     * Parses a JSON array string into an array of longs.
     *
     * @param inputJson The JSON array string.
     * @return An array of longs.
     */
    private long[] parseJsonArrayToLongs(String inputJson) {
        String json = inputJson.replaceAll("[\\[\\]]", "");
        String[] parts = json.split(",");
        long[] ids = new long[parts.length];
        for (int i = 0; i < parts.length; i++) {
            ids[i] = Long.parseLong(parts[i].trim());
        }
        return ids;
    }

    /**
     * Retrieves the title of a Hacker News story by its ID.
     *
     * @param id The ID of the news story.
     * @return The title of the story. Returns an empty string if the title is not found or in case of an error.
     */
    public String news(long id) {
        String url = "https://hacker-news.firebaseio.com/v0/item/" + id + ".json";
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return extractTitleFromJson(response.body());
        } catch (IOException | InterruptedException e) {
            return "";
        }
    }

    /**
     * Extracts the title from a JSON string using a regular expression.
     *
     * @param json The JSON string.
     * @return The extracted title, or an empty string if not found.
     */
    private String extractTitleFromJson(String json) {
        Pattern pattern = Pattern.compile("\"title\":\"(.*?)\"");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    public HackerNews(HttpClient httpClient) {
        this.httpClient = httpClient;
    }
}
