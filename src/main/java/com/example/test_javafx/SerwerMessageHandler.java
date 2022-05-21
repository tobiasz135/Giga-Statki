package com.example.test_javafx;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class SerwerMessageHandler extends Thread implements Serializable {
    ObjectOutputStream dos;
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
                    dos = Serwer.gracze[i].out;
                    dos.writeObject(dataPackage);
                    System.out.println(Serwer.CONNECTED_USERS);
                    //dos.reset();

                }
                sleep(1000);

            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
