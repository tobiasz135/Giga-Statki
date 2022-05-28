package com.example.test_javafx;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class HelloApplication extends Application {
    public static Button[][] buttons = new Button[Serwer.WIDTH][Serwer.HEIGHT];
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

        for (int i = 0; i < Serwer.WIDTH; i++) {
            for (int j = 0; j < Serwer.HEIGHT; j++) {
                buttons[i][j] = new Button();
                buttons[i][j].setMinWidth(32);
                buttons[i][j].setMinHeight(32);
                int i1 = i;
                int j1 = j;
                buttons[i][j].setOnMouseClicked(mouseEvent -> {
                    sendMissle(i1, j1);
                });
                //buttons[i][j].removeEventHandler(buttons[i][j].getOnMouseClicked());
                gridPane.add(buttons[i][j], i, j, 1, 1);
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

    public static void drawHits(DataPackage dataPackage) {
        for (int i = 0; i < Serwer.WIDTH; i++) {
            for (int j = 0; j < Serwer.HEIGHT; j++) {
                if (dataPackage.hits[i][j])
                    buttons[i][j].setGraphic(new ImageView(String.valueOf(HelloApplication.class.getResource("ships_img/explosion/exp.gif"))));
                    //buttons[i][j].setText("-");
            }

        }
        for (int i = 0; i < dataPackage.connected_users; i++) {
            for (int j = 0; j < 4; j++) {
                if (dataPackage.gracze[i].stateks[j].vertical) {
                    for (int k = dataPackage.gracze[i].stateks[j].start_y; k < dataPackage.gracze[i].stateks[j].end_y; k++) {
                        if (dataPackage.hits[dataPackage.gracze[i].stateks[j].start_x][k]) {
                            //if(dataPackage.gracze[i].stateks[j].owner!=clientReceiver.Client.getLocalPort())
                            //buttons[dataPackage.gracze[i].stateks[j].start_x][k].setText("*");
                            buttons[dataPackage.gracze[i].stateks[j].start_x][k].
                                    setGraphic(new ImageView(String.valueOf(HelloApplication.class.
                                            getResource("ships_img/explosion/exp.gif"))));
                            //else{
                            //System.out.println("before Mouse event changed");
                            //buttons[dataPackage.gracze[i].stateks[j].start_x][k].setOnMouseClicked((mouseEvent) -> {System.out.println("Mouse event changed");});
                            //}
                        }
                    }
                } else {
                    for (int k = dataPackage.gracze[i].stateks[j].start_x; k < dataPackage.gracze[i].stateks[j].end_x; k++) {
                        if (dataPackage.hits[k][dataPackage.gracze[i].stateks[j].start_y]) {
                            //if(dataPackage.gracze[i].stateks[j].owner!=clientReceiver.Client.getLocalPort())
                            //buttons[k][dataPackage.gracze[i].stateks[j].start_y].setText("*");
                            buttons[k][dataPackage.gracze[i].stateks[j].start_y].
                                    setGraphic(new ImageView(String.valueOf(HelloApplication.class.
                                            getResource("ships_img/explosion/exp.gif"))));
                            //else {
                            //buttons[k][dataPackage.gracze[i].stateks[j].start_y].setOnMouseClicked((mouseEvent) -> {
                            //System.out.println("Mouse event changed");
                            //});
                            //System.out.println("before Mouse event changed");
                        }
                        //}
//                        else
//                        {
//                            buttons[dataPackage.gracze[i].stateks[j].start_y][k].setText("-");
//                        }

                    }

                }

            }

        }

    }

    public static void drawShips(DataPackage dataPackage) {
        for (int l = 0; l < dataPackage.connected_users; l++) {
            if (clientReceiver.Client.getLocalPort() == dataPackage.gracze[l].idGracza) {
                for (int k = 0; k < 4; k++) {
                    if (dataPackage.gracze[l].stateks[k].vertical) {
                        for (int m = 0; m < dataPackage.gracze[l].stateks[k].size; m++) {
                            switch (dataPackage.gracze[l].stateks[k].size) {
                                case 2:
                                    final ImageView selectedImage1 = new ImageView(String.valueOf(HelloApplication.class.
                                            getResource("ships_img/littoral_combat_ship/parts/image_part_00" + (m + 1) + ".png")));
                                    selectedImage1.setRotate(90);
                                    buttons[dataPackage.gracze[l].stateks[k].start_x][dataPackage.gracze[l].stateks[k].start_y + m].
                                            setGraphic(selectedImage1);
                                    break;
                                case 3:
                                    final ImageView selectedImage2 = new ImageView(String.valueOf(HelloApplication.class.
                                            getResource("ships_img/destroyer/parts/image_part_00" + (m + 1) + ".png")));
                                    selectedImage2.setRotate(90);
                                    buttons[dataPackage.gracze[l].stateks[k].start_x][dataPackage.gracze[l].stateks[k].start_y + m].
                                            setGraphic(selectedImage2);
                                    break;
                                case 4:
                                    final ImageView selectedImage3 = new ImageView(String.valueOf(HelloApplication.class.
                                            getResource("ships_img/battleship/parts/image_part_00" + (m + 1) + ".png")));
                                    selectedImage3.setRotate(90);
                                    buttons[dataPackage.gracze[l].stateks[k].start_x][dataPackage.gracze[l].stateks[k].start_y + m].
                                            setGraphic(selectedImage3);
                                    break;
                                case 5:
                                    final ImageView selectedImage4 = new ImageView(String.valueOf(HelloApplication.class.
                                            getResource("ships_img/aircraft_carrier/parts/image_part_00" + (m + 1) + ".png")));
                                    selectedImage4.setRotate(90);
                                    buttons[dataPackage.gracze[l].stateks[k].start_x][dataPackage.gracze[l].stateks[k].start_y + m].
                                            setGraphic(selectedImage4);
                                    break;
                            }
                            //buttons[dataPackage.gracze[l].stateks[k].start_x][dataPackage.gracze[l].stateks[k].start_y + m].setText("X" + k);
                        }
                    } else {
                        for (int m = 0; m < dataPackage.gracze[l].stateks[k].size; m++) {
                            switch (dataPackage.gracze[l].stateks[k].size) {
                                case 2:
                                    buttons[dataPackage.gracze[l].stateks[k].start_x + m][dataPackage.gracze[l].stateks[k].start_y].
                                            setGraphic(new ImageView(String.valueOf(HelloApplication.class.
                                                    getResource("ships_img/littoral_combat_ship/parts/image_part_00" + (m + 1) + ".png"))));
                                    break;
                                case 3:
                                    final ImageView selectedImage = new ImageView(String.valueOf(HelloApplication.class.
                                            getResource("ships_img/destroyer/parts/image_part_00" + (m + 1) + ".png")));
                                    buttons[dataPackage.gracze[l].stateks[k].start_x + m][dataPackage.gracze[l].stateks[k].start_y].
                                            setGraphic(selectedImage);
                                    break;
                                case 4:
                                    buttons[dataPackage.gracze[l].stateks[k].start_x + m][dataPackage.gracze[l].stateks[k].start_y].
                                            setGraphic(new ImageView(String.valueOf(HelloApplication.class.
                                                    getResource("ships_img/battleship/parts/image_part_00" + (m + 1) + ".png"))));
                                    break;
                                case 5:
                                    buttons[dataPackage.gracze[l].stateks[k].start_x + m][dataPackage.gracze[l].stateks[k].start_y].
                                            setGraphic(new ImageView(String.valueOf(HelloApplication.class.
                                                    getResource("ships_img/aircraft_carrier/parts/image_part_00" + (m + 1) + ".png"))));
                                    break;
                            }
                            //buttons[dataPackage.gracze[l].stateks[k].start_x + m][dataPackage.gracze[l].stateks[k].start_y].setText("X" + k);
                        }
                    }
                }
            }


        }
    }

    public static void sendMissle(int x, int y) {
        try {
            System.out.println("SEND NUKES " + x + ", " + y);
            clientReceiver.dos.writeObject(new ClientDataPackege(x, y));
            //clientReceiver.dos.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void disableFriendlyFire(DataPackage dataPackage, Socket socket) {
        for (int i = 0; i < dataPackage.connected_users; i++) {
            if (dataPackage.gracze[i].idGracza == socket.getLocalPort()) {
                for (int l = 0; l < 4; l++) {
                    if (!dataPackage.gracze[i].stateks[l].vertical) {
                        for (int x = 0; x < dataPackage.gracze[i].stateks[l].size; x++) {
                            buttons[dataPackage.gracze[i].stateks[l].start_x + x][dataPackage.gracze[i].stateks[l].start_y].setDisable(true);
                            buttons[dataPackage.gracze[i].stateks[l].start_x + x][dataPackage.gracze[i].stateks[l].start_y].setOpacity(1.0);
                        }
                    } else {
                        for (int x = 0; x < dataPackage.gracze[i].stateks[l].size; x++) {
                            buttons[dataPackage.gracze[i].stateks[l].start_x][dataPackage.gracze[i].stateks[l].start_y + x].setDisable(true);
                            buttons[dataPackage.gracze[i].stateks[l].start_x][dataPackage.gracze[i].stateks[l].start_y + x].setOpacity(1.0);
                        }
                    }
                }
            }
        }
    }

    public static void toggleInput(DataPackage dataPackage, Socket socket){
        if(dataPackage.turn == socket.getLocalPort()){
            for(int i = 0; i < Serwer.WIDTH; i++){
                for(int j = 0; j < Serwer.HEIGHT; j++){
                    String PartNumber = new String("00");
                    PartNumber += (j * Serwer.WIDTH) + i + 1;
                    if(PartNumber.length() > 3) {
                        PartNumber = PartNumber.substring(PartNumber.length() - 3);
                    }
                    buttons[i][j].setGraphic(new ImageView(String.valueOf(HelloApplication.class.
                            getResource("ships_img/background/parts/image_part_" + PartNumber + ".png"))));
                    buttons[i][j].setDisable(false);
                }
            }
        }
        else{
            for(int i = 0; i < Serwer.WIDTH; i++){
                for(int j = 0; j < Serwer.HEIGHT; j++){
                    String PartNumber = new String("00");
                    PartNumber += (j * Serwer.WIDTH) + i + 1;
                    if(PartNumber.length() > 3) {
                        PartNumber = PartNumber.substring(PartNumber.length() - 3);
                    }
                    buttons[i][j].setGraphic(new ImageView(String.valueOf(HelloApplication.class.
                            getResource("ships_img/background/parts/image_part_" + PartNumber + ".png"))));
                    buttons[i][j].setDisable(true);
                }
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }


}