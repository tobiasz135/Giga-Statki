package com.example.test_javafx;

import java.io.NotSerializableException;
import java.io.Serializable;

public class DataPackage implements Serializable {
    DataPackage() throws NotSerializableException {}
    public boolean[][] hits = Serwer.hits;
    public int turn = Serwer.tura;
    public int connected_users = Serwer.CONNECTED_USERS;
    //public Statek[] stateks = new Statek[12];
    public Gracz[] gracze = Serwer.gracze;

}
