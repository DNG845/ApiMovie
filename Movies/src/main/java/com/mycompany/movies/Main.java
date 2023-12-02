/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.movies;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main {
    public static void main(String[] args) {
        MovieApp movieApp = new MovieApp();
        MovieAPIManager movieAPIManager = new MovieAPIManager();
        Cast cast = new Cast();
        Movie movie = new Movie();

        // Call methods on the objects here, for example:
        // movieApp.start();
        // movieAPIManager.fetchMovies();
        // etc.
    }
}
