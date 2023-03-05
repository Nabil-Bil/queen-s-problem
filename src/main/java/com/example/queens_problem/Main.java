package com.example.queens_problem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1020, 768);

        stage.setTitle("Queens problem");
        stage.setResizable(false);
        stage.setScene(scene);
        Canvas chess = (Canvas) scene.lookup("#chess");
        Chess.drawChess(4, chess);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}