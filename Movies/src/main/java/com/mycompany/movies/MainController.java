/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.movies;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import java.util.ArrayList;

public class MainController {
    @FXML
    private TextField titleSearchField;
    @FXML
    private Button searchButton;
    @FXML
    private ListView<Pelicula> movieListView;

    private ControladorPeliculas controladorPeliculas = new ControladorPeliculas();

    @FXML
    public void initialize() {
        // Load the initial movie data
        if (controladorPeliculas.guardarDatos()) {
            updateMovieList(controladorPeliculas.getPeliculas());
        } else {
            System.out.println("Failed to load movie data");
        }
    }

    @FXML
    public void searchMovies() {
        // Put the code for searching movies here. This method is called when the searchButton is clicked.
        // For now, this just reloads the initial movie data
        if (controladorPeliculas.guardarDatos()) {
            updateMovieList(controladorPeliculas.getPeliculas());
        } else {
            System.out.println("Failed to load movie data");
        }
    }

    private void updateMovieList(ArrayList<Pelicula> peliculas) {
        movieListView.getItems().clear();
        for (Pelicula pelicula : peliculas) {
            movieListView.getItems().add(pelicula);
        }
    }
}