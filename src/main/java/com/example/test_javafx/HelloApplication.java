package com.example.test_javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
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
                buttons[i][j].setMinWidth(34);
                buttons[i][j].setMinHeight(34);
                buttons[i][j].setMaxWidth(34);
                buttons[i][j].setMaxHeight(34);
                gridPane.add(buttons[i][j], j, i, 1, 1);
                String PartNumber = new String("00");
                PartNumber += (i * Serwer.WIDTH) + j + 1;
                if(PartNumber.length() > 3) {
                    PartNumber = PartNumber.substring(PartNumber.length() - 3);
                }
                buttons[i][j].setGraphic(new ImageView(String.valueOf(getClass().getResource("img/background_parts/image_part_" + PartNumber + ".png"))));
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