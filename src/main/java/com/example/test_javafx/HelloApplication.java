package com.example.test_javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        GridPane gridPane = new GridPane();

        Button[][] buttons = new Button[Serwer.HEIGHT][Serwer.WIDTH];
        for (int i = 0; i < Serwer.HEIGHT; i++) {
            for (int j = 0; j < Serwer.WIDTH; j++) {
                buttons[i][j] = new Button();
                buttons[i][j].setMinWidth(32);
                buttons[i][j].setMinHeight(32);
                gridPane.add(buttons[i][j], j, i, 1, 1);
            }
        }

        Scene scene = new Scene(gridPane, 512, 288);


        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}