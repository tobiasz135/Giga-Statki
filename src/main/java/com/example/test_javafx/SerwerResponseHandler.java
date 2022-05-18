package com.example.test_javafx;

import java.io.DataInputStream;
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
                Object obj = dis.readObject();
                System.out.print("DEBUG");
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
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }
}
