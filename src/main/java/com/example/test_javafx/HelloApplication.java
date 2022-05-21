package com.example.test_javafx;

import javafx.application.Application;
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
import java.net.Socket;

public class HelloApplication extends Application {
    public static Button[][] buttons = new Button[Serwer.HEIGHT][Serwer.WIDTH];
    public static ClientReceiver clientReceiver;

    static {
        try {
            clientReceiver = new ClientReceiver();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {

        clientReceiver.start();
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        BorderPane border = new BorderPane();


        GridPane grid = new GridPane();
        GridPane gridPane = new GridPane();
        GridPane players = new GridPane();
        FileInputStream input = new FileInputStream("Webp.net-resizeimage.jpg");
        Image image = new Image(input);
        ImageView[] imageView = new ImageView[Serwer.MAX_PLAYERS];


        HBox[] hBox = new HBox[Serwer.MAX_PLAYERS];
        for (int i = 0; i < Serwer.MAX_PLAYERS; i++) {
            imageView[i] = new ImageView(image);
            hBox[i] = new HBox(imageView[i]);
            hBox[i].setPrefWidth(170);
            players.add(hBox[i], i, 1);
        }

        for (int i = 0; i < Serwer.HEIGHT; i++) {
            for (int j = 0; j < Serwer.WIDTH; j++) {
                buttons[i][j] = new Button();
                buttons[i][j].setMinWidth(32);
                buttons[i][j].setMinHeight(32);
                int i1 = i;
                int j1 = j;
                buttons[i][j].setOnMouseClicked(mouseEvent ->{
                    sendMissle(j1, i1);
                });
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

    public static void drawHits(ClientReceiver clientReceiver) {
        for (int i = 0; i < Serwer.HEIGHT; i++) {
            for (int j = 0; j < Serwer.WIDTH; j++) {
                if (Serwer.hits[i][j]) {
                    //Jakiś overlay
                }
            }
        }
    }

    public static void drawShips(DataPackage dataPackage) {
        for (int l = 0; l < dataPackage.connected_users; l++) {
            if (clientReceiver.Client.getLocalPort() == dataPackage.gracze[l].idGracza) {
            for (int k = 0; k < 4; k++) {
                if (!dataPackage.gracze[l].stateks[k].vertical) {
                    for (int m = dataPackage.gracze[l].stateks[k].start_x; m < dataPackage.gracze[l].stateks[k].end_x; m++) {
                        buttons[dataPackage.gracze[l].stateks[k].start_y][m].setText("X" + k);
                    }
                } else {
                    for (int m = dataPackage.gracze[l].stateks[k].start_y; m < dataPackage.gracze[l].stateks[k].end_y; m++) {
                        buttons[m][dataPackage.gracze[l].stateks[k].start_x].setText("X" + k);
                    }
                }
            }
            }


        }
    }

    public static void sendMissle(int x, int y){
        try {
            System.out.println("SEND NUKES " + x + ", " + y);
            clientReceiver.dos.writeObject(new ClientDataPackege(x, y));
            clientReceiver.dos.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}