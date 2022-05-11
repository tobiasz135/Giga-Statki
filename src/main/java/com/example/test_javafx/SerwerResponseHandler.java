package com.example.test_javafx;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class SerwerResponseHandler extends Thread {
    final DataInputStream dis;
    //final DataOutputStream dos;
    final Socket s;

    public SerwerResponseHandler(DataInputStream dis, Socket s) {
        this.dis = dis;
        this.s = s;
    }

    public void run() {
        String received;
        String timestamp;

        while (true) {
            try {
                received = dis.readUTF();
                if(received.equals("Exit"))
                {
                    System.out.println("Closing this connection : " + s);
                    s.close();
                    System.out.println("Connection closed");
                    break;
                }

                //System.out.println(timestamp + "Klient " + s.getPort() + " : " + received);

            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

        }

    }
}
