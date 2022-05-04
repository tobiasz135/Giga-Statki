package com.example.test_javafx;

import java.util.Random;

public class Serwer extends Thread{
    public static Gracz[] gracze = new Gracz[3];
    public static int tura;
    public static boolean hits[][] = new boolean[16][9];
    public static boolean shipPlacement[][] = new boolean[16][9];

    public static void main(String[] args){

        generateShips();

    }

    private static void generateShips() {
        Random random_x = new Random();
        Random random_y = new Random();
        Random orient = new Random();
        for(int i = 0; i < 3; i++){
            boolean free = false;
            int shipSize = 5;
            for(int l = 0; l < 4;l++){
                boolean o = orient.nextBoolean();
                int x = random_x.nextInt(16);
                int y = random_y.nextInt(9);
                if(o){  // horizontal
                    if(x + shipSize < 16){
                        for(int k = 0; k < shipSize; k++){
                            if(!shipPlacement[y][x + k]){
                                free = true;
                            }
                            else
                                free = false;
                        }
                        if(!free){
                            l--;
                            continue;
                        }
                        else{
                            for(int k = 0; k < shipSize; k++){
                                shipPlacement[y][x + k] = true;
                            }
                            gracze[i].stateks[l].start_x = x;
                            gracze[i].stateks[l].start_y = y;
                            gracze[i].stateks[l].end_x = x + shipSize;
                            gracze[i].stateks[l].end_y = y;
                            gracze[i].stateks[l].owner = gracze[i].idGracza;
                            gracze[i].stateks[l].size = shipSize;
                            gracze[i].stateks[l].sank = 0;
                            shipSize--;
                        }
                    }
                    else{
                        l--;
                        continue;
                    }
                }
                else{   // vertical
                    if(y + shipSize < 9){
                        for(int k = 0; k < shipSize; k++){
                            if(!shipPlacement[y + k][x]){
                                free = true;
                            }
                            else
                                free = false;
                        }
                        if(!free){
                            l--;
                            continue;
                        }
                        else{
                            for(int k = 0; k < shipSize; k++){
                                shipPlacement[y+k][x] = true;
                            }
                            gracze[i].stateks[l].start_x = x;
                            gracze[i].stateks[l].start_y = y;
                            gracze[i].stateks[l].end_x = x;
                            gracze[i].stateks[l].end_y = y + shipSize;
                            gracze[i].stateks[l].owner = gracze[i].idGracza;
                            gracze[i].stateks[l].size = shipSize;
                            gracze[i].stateks[l].sank = 0;
                            shipSize--;
                        }
                    }
                    else{
                        l--;
                        continue;
                    }
                }

            }
        }
    }


}
