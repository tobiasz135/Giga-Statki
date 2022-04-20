package com.example.test_javafx;

import java.util.Random;

public class Serwer extends Thread{
    public static Gracz[] gracze = new Gracz[3];
    public static int tura;
    public static boolean hits[][] = new boolean[16][9];


    public static void main(){
        generateShips();
    }

    private static void generateShips() {
        Random random_x = new Random();
        Random random_y = new Random();
        Random orient = new Random();
        int shipSize = 5;
        for(int i = 0; i < 3; i++){
            for(int l = 0; l < 4;l++){
                boolean o = orient.nextBoolean();
                if(o){

                }

                gracze[i].stateks[l]
            }
        }
    }


}
