/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.movies;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class MovieApp extends Application {
    private static final String API_KEY = "319aace89a56c7c8d21775de86bc77de";
    private static final String BEARER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzMTlhYWNlODlhNTZjN2M4ZDIxNzc1ZGU4NmJjNzdkZSIsInN1YiI6IjY1NmI1YjRhNjUxN2Q2MDE1MTY1MGUxYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.bko96yMUUSf_cHeYjBvZG4McHqPhE8dSzQU5tqqAU90";
    private static final String TRENDING_MOVIES_URL = "https://api.themoviedb.org/3/trending/movie/day?language=en-US";

    @Override
    public void start(Stage primaryStage) {
        ListView<String> movieListView = new ListView<>();
        Label errorLabel = new Label();

        // Home menu
        try {
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

            List<Movie> movies = new ArrayList<>();
            for (int i = 0; i < results.size(); i++) {
                JsonObject movieJson = results.get(i).getAsJsonObject();
                String title = movieJson.get("title").getAsString();
                String year = movieJson.get("release_date").getAsString().split("-")[0]; // Assuming the date is in the format "yyyy-mm-dd"
                Movie movie = new Movie(title, year);
                movies.add(movie);
            }

            for (Movie movie : movies) {
                movieListView.getItems().add(movie.getTitle() + " (" + movie.getYear() + ")");
            }
        } catch (JsonSyntaxException | IOException | InterruptedException | URISyntaxException e) {
            errorLabel.setText("An error occurred: " + e.getMessage());
        }

        // Search section
        TextField titleSearchField = new TextField();
        Button searchButton = new Button("Search");
        searchButton.setOnAction(event -> {
            try {
                // Search for movies
                String title = titleSearchField.getText();
                String searchUrl = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&query=" + title;
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

                List<Movie> movies = new ArrayList<>();
                for (int i = 0; i < results.size(); i++) {
                    JsonObject movieJson = results.get(i).getAsJsonObject();
                    String movieTitle = movieJson.get("title").getAsString();
                    String year = movieJson.get("release_date").getAsString().split("-")[0]; // Assuming the date is in the format "yyyy-mm-dd"
                    Movie movie = new Movie(movieTitle, year);
                    movies.add(movie);
                }

                movieListView.getItems().clear();
                for (Movie movie : movies) {
                    movieListView.getItems().add(movie.getTitle() + " (" + movie.getYear() + ")");
                }
            } catch (JsonSyntaxException | IOException | InterruptedException | URISyntaxException e) {
                errorLabel.setText("An error occurred: " + e.getMessage());
            }
        });

        GridPane searchPane = new GridPane();
        searchPane.setPadding(new Insets(10, 10, 10, 10));
        searchPane.setVgap(10);
        searchPane.setHgap(10);
        searchPane.add(titleSearchField, 0, 0);
        searchPane.add(searchButton, 1, 0);

        BorderPane root = new BorderPane();
        root.setTop(searchPane);
        root.setCenter(movieListView);
        root.setBottom(errorLabel);

        Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("Movie App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
