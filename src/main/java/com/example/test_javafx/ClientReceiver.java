package com.example.test_javafx;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.io.*;
import java.net.Socket;

public class ClientReceiver extends Thread implements Serializable {
    public static DataPackage dataPackage;
    String ip="localhost";
    int port=5056;
    Socket Client=new Socket(ip,port);
    ObjectInputStream dis = new ObjectInputStream(Client.getInputStream());
    ObjectOutputStream dos = new ObjectOutputStream(Client.getOutputStream());


    public ClientReceiver() throws IOException {
        //super("Problem z polaczeniem");
        //dis = new ObjectInputStream(Client.getInputStream());
    }
    public void updateScore(){
        for (int i = 0; i < dataPackage.connected_users; i++) {
            if(ClientReceiver.dataPackage.gracze[i].color==1)
                HelloApplication.hBox[i].setBackground(new Background(new BackgroundFill(Color.RED,CornerRadii.EMPTY, Insets.EMPTY)));
            if(ClientReceiver.dataPackage.gracze[i].color==2)
                HelloApplication.hBox[i].setBackground(new Background(new BackgroundFill(Color.GREEN,CornerRadii.EMPTY, Insets.EMPTY)));
            if(ClientReceiver.dataPackage.gracze[i].color==3)
                HelloApplication.hBox[i].setBackground(new Background(new BackgroundFill(Color.FUCHSIA,CornerRadii.EMPTY, Insets.EMPTY)));


        }
        int alive=0;
        int index=-1;
        for (int i = 0; i < dataPackage.connected_users; i++) {
            HelloApplication.score[i]=4;
            for (int j = 0; j < 4; j++) {
                if(!dataPackage.gracze[i].stateks[j].isAlive(dataPackage.hits))
                    HelloApplication.score[i]--;
                if(HelloApplication.score[i]==0)
                    HelloApplication.hBox[i].setOpacity(0.4);
                //HelloApplication.labels[i].setText("WINNER!!!");
            }
            if(HelloApplication.score[i]!=0)
            {
                alive++;
                index=i;
            }

            HelloApplication.labels[i].setText("Statki: "+ HelloApplication.score[i]);
            System.out.println(i+": "+HelloApplication.score[i]);
        }
        if(alive==1&&dataPackage.connected_users>1)
        {
            HelloApplication.hBox[index].setBackground(new Background(new BackgroundFill(Color.YELLOW,CornerRadii.EMPTY, Insets.EMPTY)));
            HelloApplication.labels[index].setText("WINNER!!!");
        }

    }


    public void run() {
        //DataPackage dataPackage;
        try {
            while (true) {
                //Object obj = dis.readObject();
                //System.out.print(obj);
                dataPackage = (DataPackage) dis.readObject();
                System.out.println(dataPackage);
                if (dataPackage != null) {
                    //System.out.println("Odebrano: " + dataPackage.gracze.get(0).getNick());
                    System.out.println(dataPackage.connected_users);
                    for(int i = 0; i < 4; i++){
                        System.out.println(dataPackage.gracze[0].stateks[i].size);
                    }

                    System.out.println(Client.getLocalPort());
                    System.out.println(dataPackage.gracze[0].idGracza);
                }
                System.out.println("TURN: " + dataPackage.turn);
                System.out.println("MY PORT:" + Client.getLocalPort());
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        HelloApplication.toggleInput(dataPackage, Client);
                        HelloApplication.disableFriendlyFire(dataPackage, Client);
                        HelloApplication.drawShips(dataPackage);
                        HelloApplication.drawHits(dataPackage);
                        updateScore();
                        System.out.println(dataPackage.connected_users);
                    }
                });

                //dis.reset();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
