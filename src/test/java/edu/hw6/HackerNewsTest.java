package edu.hw6;

import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class HackerNewsTest {

    @Test
    @DisplayName("TopStories online test")
    public void testGettingTopStoriesList() {
        HackerNews hackerNews = new HackerNews(HttpClient.newHttpClient());
        assertThat(hackerNews.hackerNewsTopStories()).isNotEmpty();
    }

    @Test
    @DisplayName("NewsTitle online test")
    public void testGettingTitle() {
        HackerNews hackerNews = new HackerNews(HttpClient.newHttpClient());
        assertThat(hackerNews.news(37570037)).isEqualTo("JDK 21 Release Notes");
    }

    @Test
    @DisplayName("TopStories mock test")
    public void testHackerNewsTopStoriesMock() throws Exception {
        HttpClient mockHttpClient = Mockito.mock(HttpClient.class);
        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn("[12345, 67890]");
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandlers.ofString().getClass())))
            .thenReturn(mockResponse);

        HackerNews hackerNews = new HackerNews(mockHttpClient);
        long[] expectedIds = new long[]{12345L, 67890L};
        assertArrayEquals(expectedIds, hackerNews.hackerNewsTopStories());
    }

    @Test
    @DisplayName("NewsTitle mock test")
    public void testNewsMock() throws Exception {
        HttpClient mockHttpClient = Mockito.mock(HttpClient.class);
        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn("{\"id\":12345, \"title\":\"Test News Title\"}");
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandlers.ofString().getClass())))
            .thenReturn(mockResponse);

        HackerNews hackerNews = new HackerNews(mockHttpClient);
        assertThat(hackerNews.news(12345)).isEqualTo("Test News Title");
    }

}
