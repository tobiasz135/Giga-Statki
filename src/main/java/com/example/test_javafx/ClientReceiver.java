package com.example.test_javafx;

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
                    System.out.println(Client.getLocalPort());
                    System.out.println(dataPackage.gracze[0].idGracza);
                }
                HelloApplication.drawShips(dataPackage);

                //dis.reset();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
