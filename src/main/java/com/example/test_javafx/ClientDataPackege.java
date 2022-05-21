package com.example.test_javafx;

import java.io.Serializable;

public class ClientDataPackege implements Serializable {
    public int x;
    public int y;

    ClientDataPackege(int x, int y){
        this.x = x;
        this.y = y;
    }
}
