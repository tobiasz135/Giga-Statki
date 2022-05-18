package com.example.test_javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


import java.io.FileInputStream;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ClientReceiver clientReceiver = new ClientReceiver();
        clientReceiver.start();
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        BorderPane border = new BorderPane();


        GridPane grid = new GridPane();
        GridPane gridPane = new GridPane();
        GridPane players=new GridPane();
        FileInputStream input = new FileInputStream("Webp.net-resizeimage.jpg");
        Image image = new Image(input);
        ImageView[] imageView = new ImageView[Serwer.MAX_PLAYERS];




        HBox[] hBox = new HBox[Serwer.MAX_PLAYERS];
        for (int i = 0; i < Serwer.MAX_PLAYERS; i++) {
                imageView[i] = new ImageView(image);
             hBox[i] = new HBox(imageView[i]);
             hBox[i].setPrefWidth(170);
             players.add(hBox[i],i,1);
        }
        Button[][] buttons = new Button[Serwer.HEIGHT][Serwer.WIDTH];
        for (int i = 0; i < Serwer.HEIGHT; i++) {
            for (int j = 0; j < Serwer.WIDTH; j++) {
                buttons[i][j] = new Button();
                buttons[i][j].setMinWidth(32);
                buttons[i][j].setMinHeight(32);
                gridPane.add(buttons[i][j], j, i, 1, 1);
            }
        }
        border.setTop(players);
        border.setCenter(gridPane);
        Scene scene = new Scene(border, 512, 458);
        //Scene scene2 = new Scene(gridPane, 512, 500);
        stage.setTitle("Statki");
        stage.setScene(scene);
        //stage.setScene(scene2);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}