package com.example.test_javafx;

public class Gracz {
    public Statek stateks[];
    public int idGracza;

    Gracz(){
        stateks = new Statek[4];
        stateks[0] = new Statek();
        stateks[1] = new Statek();
        stateks[2] = new Statek();
        stateks[3] = new Statek();
    }
}
