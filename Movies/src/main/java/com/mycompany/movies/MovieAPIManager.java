/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.movies;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class MovieAPIManager {
    private static final String API_KEY = "319aace89a56c7c8d21775de86bc77de";
    private static final String BEARER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzMTlhYWNlODlhNTZjN2M4ZDIxNzc1ZGU4NmJjNzdkZSIsInN1YiI6IjY1NmI1YjRhNjUxN2Q2MDE1MTY1MGUxYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.bko96yMUUSf_cHeYjBvZG4McHqPhE8dSzQU5tqqAU90";
    private static final String TRENDING_MOVIES_URL = "https://api.themoviedb.org/3/trending/movie/day?language=en-US";

    public List<Pelicula> getTrendingMovies() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(TRENDING_MOVIES_URL))
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + BEARER_TOKEN)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Parse the response body here and create Movie objects.
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(response.body()).getAsJsonObject();
        JsonArray results = jsonObject.getAsJsonArray("results");

        List<Pelicula> movies = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            JsonObject movieJson = results.get(i).getAsJsonObject();
            String title = movieJson.get("title").getAsString();
            String overview = movieJson.get("overview").getAsString();
            String posterPath = movieJson.get("poster_path").getAsString();
            Pelicula movie = new Pelicula(title, overview, posterPath);
            movies.add(movie);
        }

        return movies;
    }

    public List<Pelicula> searchMovies(String title) throws Exception {String searchUrl = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&query=" + title;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(searchUrl))
                .header("accept", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Parse the response body here and create Movie objects.
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(response.body()).getAsJsonObject();
        JsonArray results = jsonObject.getAsJsonArray("results");

        List<Pelicula> movies = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            JsonObject movieJson = results.get(i).getAsJsonObject();
            String movieTitle = movieJson.get("title").getAsString();
            String overview = movieJson.get("overview").getAsString();
            String posterPath = movieJson.get("poster_path").getAsString();
            Pelicula movie = new Pelicula(movieTitle, overview, posterPath);
            movies.add(movie);
        }

        return movies;
    }
}
