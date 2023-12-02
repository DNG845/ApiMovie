/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danny
 */
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

public class MovieAPIManager {
    private static final String API_KEY = "319aace89a56c7c8d21775de86bc77de";

    public Movie getMovieInfo(int collectionId) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.themoviedb.org/3/collection/" + collectionId + "?api_key=" + API_KEY))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Parse the response body here and create a Movie object.
        JSONObject jsonObject = new JSONObject(response.body());
        Movie movie = new Movie();
        // Set the fields of the movie object based on the jsonObject.

        return movie;
    }

    public MovieList getMovieList() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.themoviedb.org/3/movie/changes?api_key=" + API_KEY))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Parse the response body here and create a MovieList object.
        JSONObject jsonObject = new JSONObject(response.body());
        MovieList movieList = new MovieList();
        // Set the fields of the movieList object based on the jsonObject.

        return movieList;
    }
}
