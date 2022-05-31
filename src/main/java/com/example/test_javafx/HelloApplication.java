package com.example.test_javafx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class HelloApplication extends Application {
    public static Button[][] buttons = new Button[Serwer.WIDTH][Serwer.HEIGHT];
    public static ClientReceiver clientReceiver;
    public static int[] score=new int[3];
    public static Label[] labels=new Label[3];
    public static HBox[] hBox = new HBox[Serwer.MAX_PLAYERS];

    static {
        try {
            clientReceiver = new ClientReceiver();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        for (int i = 0; i < 3; i++) {
            labels[i]=new Label("Statki: 4");
            //labels[i].setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
        }

        clientReceiver.start();
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        BorderPane border = new BorderPane();


        GridPane grid = new GridPane();
        GridPane gridPane = new GridPane();
        GridPane players = new GridPane();
        players.setBackground(new Background(new BackgroundFill(Color.GRAY,CornerRadii.EMPTY, Insets.EMPTY)));
        players.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
        int h=3;
        ImageView[] imageView = new ImageView[Serwer.MAX_PLAYERS];


        for (int i = 0; i < Serwer.MAX_PLAYERS; i++) {
            imageView[i] = new ImageView(String.valueOf(HelloApplication.class.
                    getResource("avatars/av"+(i+3)+".gif")));
            hBox[i] = new HBox(imageView[i]);
            hBox[i].setPrefWidth(170);
            hBox[i].setBackground(new Background(new BackgroundFill(Color.GRAY,CornerRadii.EMPTY, Insets.EMPTY)));
            players.add(hBox[i], i, 0);
        }

        for (int i = 0; i < Serwer.WIDTH; i++) {
            for (int j = 0; j < Serwer.HEIGHT; j++) {
                buttons[i][j] = new Button();
                buttons[i][j].setMinWidth(32);
                buttons[i][j].setMaxWidth(32);
                buttons[i][j].setMinHeight(32);
                buttons[i][j].setMaxHeight(32);
                int i1 = i;
                int j1 = j;
                buttons[i][j].setOnMouseClicked(mouseEvent -> {
                    sendMissle(i1, j1);
                });
                //buttons[i][j].removeEventHandler(buttons[i][j].getOnMouseClicked());
                gridPane.add(buttons[i][j], i, j+1, 1, 1);
            }
        }
        for (int i = 0; i < 3; i++) {
            players.add(labels[i],i,1);
        }
        border.setTop(players);
        border.setCenter(gridPane);
        Scene scene = new Scene(border, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
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
                    buttons[i][j].setGraphic(new ImageView(String.valueOf(HelloApplication.class.
                            getResource("bubbles.gif"))));
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
                            ImageView imageView=new ImageView(String.valueOf(HelloApplication.class.
                                    getResource("ezgif.com-gif-maker-5.gif")));
                            imageView.setY(20);
                            buttons[dataPackage.gracze[i].stateks[j].start_x][k].
                                    setGraphic(imageView);
                            buttons[dataPackage.gracze[i].stateks[j].start_x][k].setBackground(new Background(new BackgroundImage(new Image(String.valueOf(HelloApplication.class.
                                    getResource("ships_img/background/ezgif.com-gif-maker.jpg"))),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                    BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));
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
                            ImageView imageView=new ImageView(String.valueOf(HelloApplication.class.
                                    getResource("ezgif.com-gif-maker-5.gif")));
                            imageView.setY(20);
                            buttons[k][dataPackage.gracze[i].stateks[j].start_y].
                                    setGraphic(imageView);
                            buttons[k][dataPackage.gracze[i].stateks[j].start_y].setBackground(new Background(new BackgroundImage(new Image(String.valueOf(HelloApplication.class.
                                    getResource("ships_img/background/ezgif.com-gif-maker.jpg"))),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                    BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));
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
        String userColor = null;
        for (int l = 0; l < dataPackage.connected_users; l++) {
            if (clientReceiver.Client.getLocalPort() == dataPackage.gracze[l].idGracza) {
                switch (dataPackage.gracze[l].color) {
                    case 1 -> userColor = "red";
                    case 2 -> userColor = "green";
                    case 3 -> userColor = "fushia";
                }
                for (int k = 0; k < 4; k++) {
                    if (dataPackage.gracze[l].stateks[k].vertical) {
                        for (int m = 0; m < dataPackage.gracze[l].stateks[k].size; m++) {
                            switch (dataPackage.gracze[l].stateks[k].size) {
                                case 2 -> {
                                    Image image1=new Image(String.valueOf(HelloApplication.class.
                                            getResource("ships_img/littoral_combat_ship/" + userColor + "Parts/image_part_00" + (m + 1) + ".png")));
                                    final ImageView selectedImage1 = new ImageView(image1);
                                    selectedImage1.setRotate(90);
                                    image1=selectedImage1.snapshot(null,null);
                                    buttons[dataPackage.gracze[l].stateks[k].start_x][dataPackage.gracze[l].stateks[k].start_y + m].setBackground(new Background(new BackgroundImage(image1,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                            BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));
                                }
                                case 3 -> {
                                    Image image2 = new Image(String.valueOf(HelloApplication.class.
                                            getResource("ships_img/destroyer/" + userColor + "Parts/image_part_00" + (m + 1) + ".png")));
                                    final ImageView selectedImage2=new ImageView(image2);
                                    selectedImage2.setRotate(90);
                                    image2=selectedImage2.snapshot(null,null);
                                    buttons[dataPackage.gracze[l].stateks[k].start_x][dataPackage.gracze[l].stateks[k].start_y + m].setBackground(new Background(new BackgroundImage(image2,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                            BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));
                                }
                                case 4 -> {
                                    Image image3 = new Image(String.valueOf(HelloApplication.class.
                                            getResource("ships_img/battleship/" + userColor + "Parts/image_part_00" + (m + 1) + ".png")));
                                    final ImageView selectedImage3=new ImageView(image3);
                                    selectedImage3.setRotate(90);
                                    image3=selectedImage3.snapshot(null,null);
                                    buttons[dataPackage.gracze[l].stateks[k].start_x][dataPackage.gracze[l].stateks[k].start_y + m].setBackground(new Background(new BackgroundImage(image3,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                            BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));
                                }
                                case 5 -> {
                                    Image image4 = new Image(String.valueOf(HelloApplication.class.
                                            getResource("ships_img/aircraft_carrier/" + userColor + "Parts/image_part_00" + (m + 1) + ".png")));
                                    final ImageView selectedImage4=new ImageView(image4);
                                    selectedImage4.setRotate(90);
                                    image4=selectedImage4.snapshot(null,null);
                                    buttons[dataPackage.gracze[l].stateks[k].start_x][dataPackage.gracze[l].stateks[k].start_y + m].setBackground(new Background(new BackgroundImage(image4,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                            BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));
                                }
                            }
                            //buttons[dataPackage.gracze[l].stateks[k].start_x][dataPackage.gracze[l].stateks[k].start_y + m].setText("X" + k);
                        }
                    } else {
                        for (int m = 0; m < dataPackage.gracze[l].stateks[k].size; m++) {
                            switch (dataPackage.gracze[l].stateks[k].size) {
                                case 2 ->
                                        {Image image1 =new Image(String.valueOf(HelloApplication.class.
                                                getResource("ships_img/littoral_combat_ship/" + userColor + "Parts/image_part_00" + (m + 1) + ".png")));
                                            ImageView selectedImage1=new ImageView(image1);
                                            //selectedImage1.setRotate(90);
                                            image1=selectedImage1.snapshot(null,null);
                                            buttons[dataPackage.gracze[l].stateks[k].start_x + m][dataPackage.gracze[l].stateks[k].start_y].setBackground(new Background(new BackgroundImage(image1,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                                    BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));}

                                case 3 ->
                                        {Image image2 =new Image(String.valueOf(HelloApplication.class.
                                                getResource("ships_img/destroyer/" + userColor + "Parts/image_part_00" + (m + 1) + ".png")));
                                            ImageView selectedImage2=new ImageView(image2);
                                            //selectedImage2.setRotate(90);
                                            image2=selectedImage2.snapshot(null,null);
                                            buttons[dataPackage.gracze[l].stateks[k].start_x + m][dataPackage.gracze[l].stateks[k].start_y].setBackground(new Background(new BackgroundImage(image2,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                                    BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));}
                                case 4 ->{Image image3 =new Image(String.valueOf(HelloApplication.class.
                                        getResource("ships_img/battleship/" + userColor + "Parts/image_part_00" + (m + 1) + ".png")));
                                    ImageView selectedImage3=new ImageView(image3);
                                    //selectedImage3.setRotate(90);
                                    image3=selectedImage3.snapshot(null,null);
                                    buttons[dataPackage.gracze[l].stateks[k].start_x + m][dataPackage.gracze[l].stateks[k].start_y].setBackground(new Background(new BackgroundImage(image3,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                            BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));}
                                case 5 ->{ Image image4 =new Image(String.valueOf(HelloApplication.class.
                                        getResource("ships_img/aircraft_carrier/" + userColor + "Parts/image_part_00" + (m + 1) + ".png")));
                                    ImageView selectedImage4=new ImageView(image4);
                                    //selectedImage4.setRotate(90);
                                    image4=selectedImage4.snapshot(null,null);
                                    buttons[dataPackage.gracze[l].stateks[k].start_x + m][dataPackage.gracze[l].stateks[k].start_y].setBackground(new Background(new BackgroundImage(image4,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                            BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));}
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
                            //buttons[dataPackage.gracze[i].stateks[l].start_x + x][dataPackage.gracze[i].stateks[l].start_y].setOpacity(1.0);
                        }
                    } else {
                        for (int x = 0; x < dataPackage.gracze[i].stateks[l].size; x++) {
                            buttons[dataPackage.gracze[i].stateks[l].start_x][dataPackage.gracze[i].stateks[l].start_y + x].setDisable(true);
                            //buttons[dataPackage.gracze[i].stateks[l].start_x][dataPackage.gracze[i].stateks[l].start_y + x].setOpacity(1.0);
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
                    String PartNumber = "00";
                    PartNumber += (j * Serwer.WIDTH) + i + 1;
                    if(PartNumber.length() > 3) {
                        PartNumber = PartNumber.substring(PartNumber.length() - 3);
                    }
                    buttons[i][j].setBackground(new Background(new BackgroundImage(new Image(String.valueOf(HelloApplication.class.
                            getResource("ships_img/background/parts/image_part_" + PartNumber + ".png"))),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));
                    buttons[i][j].setOpacity(1);
                    buttons[i][j].setDisable(false);
                }
            }
        }
        else{
            for(int i = 0; i < Serwer.WIDTH; i++){
                for(int j = 0; j < Serwer.HEIGHT; j++){
                    String PartNumber = "00";
                    PartNumber += (j * Serwer.WIDTH) + i + 1;
                    if(PartNumber.length() > 3) {
                        PartNumber = PartNumber.substring(PartNumber.length() - 3);
                    }
                    buttons[i][j].setBackground(new Background(new BackgroundImage(new Image(String.valueOf(HelloApplication.class.
                            getResource("ships_img/background/parts/image_part_" + PartNumber + ".png"))),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));
                    buttons[i][j].setOpacity(0.6);
                    buttons[i][j].setDisable(true);
                }
            }
        }
    }

    public static void showWinner()
    {
        for (int i = 0; i < ClientReceiver.dataPackage.connected_users; i++) {
            if(ClientReceiver.dataPackage.gracze[i].winner!=-1)
            {
                hBox[i].setBackground(new Background(new BackgroundFill(Color.YELLOW,CornerRadii.EMPTY, Insets.EMPTY)));
                labels[i].setText("WINNER!!!");
            }

        }
    }

    public static void main(String[] args) {
        launch();
    }


}