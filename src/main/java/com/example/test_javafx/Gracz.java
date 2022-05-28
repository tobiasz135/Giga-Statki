package com.example.test_javafx;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class Gracz implements Serializable {
    public Statek stateks[];
    public int idGracza;
    transient ObjectInputStream in;
    transient ObjectOutputStream out;
    transient Socket socket;
    public int color;

    Gracz(){
        stateks = new Statek[4];
        stateks[0] = new Statek();
        stateks[1] = new Statek();
        stateks[2] = new Statek();
        stateks[3] = new Statek();
    }
}
