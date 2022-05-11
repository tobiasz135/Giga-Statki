package com.example.test_javafx;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class SerwerMessageHandler extends Thread {
    ObjectOutputStream dos;
    List<Socket> clients;

    public void run() {
        //String received;
        //String timestamp;
        DataPackage dataPackage = new DataPackage();
        //Scanner scn = new Scanner(System.in);

        while (true) {
            clients = Serwer.clients;
            try {
                for(int i = 0; i < clients.size(); i++){
                    dos = new ObjectOutputStream(clients.get(i).getOutputStream());
                    dos.writeObject(dataPackage);
                    System.out.println("Serwer do Klient " + clients.get(i).getPort());
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
