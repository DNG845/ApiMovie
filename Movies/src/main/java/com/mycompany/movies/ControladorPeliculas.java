/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.movies;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ControladorPeliculas {
    private ArrayList<Pelicula> peliculas;
    
    public ControladorPeliculas() {
        peliculas = new ArrayList<>();
    }

    public boolean guardarDatos(){
        try {
            URL url = new URL("https://api.themoviedb.org/3/movie/popular?api_key=319aace89a56c7c8d21775de86bc77de");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);               
            }
            reader.close();
            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
            JsonArray results = jsonObject.getAsJsonArray("results");
            for (int i = 0; i < results.size(); i++) {
                JsonObject movie = results.get(i).getAsJsonObject();
                String title = movie.get("title").getAsString();
                String originalTitle = movie.get("original_title").getAsString();
                String overview = movie.get("overview").getAsString();
                String posterPath = movie.get("poster_path").getAsString();

                Pelicula peli = new Pelicula(title, overview, posterPath);
                peliculas.add(peli);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(ArrayList<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }
}
