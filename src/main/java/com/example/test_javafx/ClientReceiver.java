package com.example.test_javafx;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientReceiver extends Thread {
    String ip="10.104.28.249";
    int port=5056;
    Socket Client=new Socket(ip,port);
    ObjectInputStream dis = new ObjectInputStream(Client.getInputStream());
    ObjectOutputStream dos = new ObjectOutputStream(Client.getOutputStream());


    public ClientReceiver() throws IOException {
        super("Problem z polaczeniem");
    }


    public void run() {
        DataPackage dataPackage=null;
        try {
            while (true) {
                dataPackage = (DataPackage) dis.readObject();
                if (dataPackage != null) {
                    //System.out.println("Odebrano: " + dataPackage.gracze.get(0).getNick());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
