package com.example.test_javafx;

import java.io.*;
import java.net.Socket;

public class SerwerClientHandler extends Thread {
    final ObjectInputStream dis;
    final ObjectOutputStream dos;
    final Socket s;

    // Constructor
    public SerwerClientHandler(Socket s, ObjectInputStream dis, ObjectOutputStream dos)
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run()
    {
        String received;
        String toreturn;
        Thread clientResponseHandler = new SerwerResponseHandler(this.dis, this.s);
        //Thread clientMessageHandler = new MessageHandler(this.dos, this.s);
        clientResponseHandler.start();
        //Serwer.CONNECTED_USERS++;
        //clientMessageHandler.start();
        try {
            clientResponseHandler.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try
        {
            // closing resources
            this.dis.close();
            this.dos.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
