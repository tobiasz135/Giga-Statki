package com.example.test_javafx;

import java.io.Serializable;

public class Statek implements Serializable {
    public int start_x = 1;
    public int start_y = 1;
    public int end_x = 1;
    public int end_y = 1;
    public int sank = 0;
    public int size = 1;
    public int owner = 1;
    public boolean vertical = false;

    private void updateShip(boolean alive){
        if(alive){
            this.sank = 1;
        }
    }

    public boolean isAlive(boolean[][] hits){
        boolean alive = true;
        if(!this.vertical){
            for(int i = 0; i < this.size; i++){
                if (hits[this.start_x + i][this.start_y]) {
                    alive = false;
                    break;
                }
            }
        }
        else{
            for(int i = 0; i < this.size; i++){
                if (hits[this.start_x][this.start_y + i]) {
                    alive = false;
                    break;
                }
            }
        }
        updateShip(alive);
        return alive;
    }
}
