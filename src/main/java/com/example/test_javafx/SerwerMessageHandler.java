package com.example.test_javafx;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class SerwerMessageHandler extends Thread implements Serializable {
    ObjectOutputStream dos;
    Socket s;
    //public static int round = ;
    //List<Socket> clients;

    public void run() {
        //String received;
        //String timestamp;

        DataPackage dataPackage = null;
        try {
            dataPackage = new DataPackage();
        } catch (NotSerializableException e) {
            e.printStackTrace();
        }

        //Scanner scn = new Scanner(System.in);
        while (true) {

            //clients = Serwer.clients;
            try {
                for(int i = 0; i < Serwer.CONNECTED_USERS; i++){
                    dataPackage.connected_users = Serwer.CONNECTED_USERS;
                    dataPackage.hits = Serwer.hits;
                    dataPackage.turn = Serwer.gracze[Serwer.tura % Serwer.CONNECTED_USERS].idGracza;
                    s = Serwer.gracze[i].socket;

                    for(int l = 0; l < 4; l++){
                        dataPackage.gracze[i].stateks[l].owner = s.getPort();
                    }
                    dos = Serwer.gracze[i].out;
                    dos.writeObject(dataPackage);
                    System.out.println(Serwer.CONNECTED_USERS);
                    dos.reset();

                }
                sleep(1000);

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                try {
                    s.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    Serwer.CONNECTED_USERS--;
                }
                //break;
            }

        }

    }
}
