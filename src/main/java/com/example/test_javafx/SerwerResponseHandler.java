package com.example.test_javafx;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class SerwerResponseHandler extends Thread {
    final ObjectInputStream dis;
    //final DataOutputStream dos;
    final Socket s;

    public SerwerResponseHandler(ObjectInputStream dis, Socket s) {
        this.dis = dis;
        this.s = s;
    }

    public void run() {
        String received;
        String timestamp;

        while (true) {
            try {
                ClientDataPackege obj = (ClientDataPackege) dis.readObject();
                //System.out.print("DEBUG");
                Serwer.hits[obj.x][obj.y]=true;
                System.out.println(obj.x + ", " + obj.y);
                Serwer.tura++;
                /*if(received.equals("Exit"))
                {
                    System.out.println("Closing this connection : " + s);
                    s.close();
                    System.out.println("Connection closed");
                    break;
                }*/

                //System.out.println(timestamp + "Klient " + s.getPort() + " : " + received);

            } catch (IOException e) {
                e.printStackTrace();
                try {
                    s.close();
                    Serwer.CONNECTED_USERS--;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }
}
