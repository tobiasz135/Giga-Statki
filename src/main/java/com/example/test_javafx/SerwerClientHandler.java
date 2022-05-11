package com.example.test_javafx;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SerwerClientHandler extends Thread {
    final DataInputStream dis;
    final ObjectOutputStream dos;
    final Socket s;

    // Constructor
    public SerwerClientHandler(Socket s, DataInputStream dis, ObjectOutputStream dos)
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
