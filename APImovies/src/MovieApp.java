/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danny
 */
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

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class MovieApp extends Application {
    private static final String API_KEY = "55700d4ab3353b4cb88e5936793a3e31";
    private static final String BEARER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1NTcwMGQ0YWIzMzUzYjRjYjg4ZTU5MzY3OTNhM2UzMSIsInN1YiI6IjY1NmI1YjRhNjUxN2Q2MDE1MTY1MGUxYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.zW1RttvUU_0aORJQMl2uBntRWnIRxhLlOti_Cq9mS5U";
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
            JsonReader jsonReader = Json.createReader(new StringReader(response.body()));
            JsonObject jsonObject = jsonReader.readObject();
            JsonArray results = jsonObject.getJsonArray("results");

            List<Movie> movies = new ArrayList<>();
            for (int i = 0; i < results.size(); i++) {
                JsonObject movieJson = results.getJsonObject(i);
                String title = movieJson.getString("title");
                String year = movieJson.getString("release_date").split("-")[0]; // Assuming the date is in the format "yyyy-mm-dd"
                Movie movie = new Movie(title, year);
                movies.add(movie);
            }

            for (Movie movie : movies) {
                movieListView.getItems().add(movie.getTitle() + " (" + movie.getYear() + ")");
            }
        } catch (Exception e) {
            errorLabel.setText("An error occurred: " + e.getMessage());
        }

        // Search section
        TextField titleSearchField = new TextField();
        Button searchButton = new Button("Search");
        searchButton.setOnAction(event -> {
            try