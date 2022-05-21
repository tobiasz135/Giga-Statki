package com.example.test_javafx;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Serwer extends Thread{
    final static int WIDTH = 16;
    final static int HEIGHT = 9;
    final static int MAX_PLAYERS = 3;
    public static Gracz[] gracze = new Gracz[MAX_PLAYERS];
    public static int tura;
    public static boolean[][] hits = new boolean[WIDTH][HEIGHT];
    public static boolean[][] shipPlacement = new boolean[WIDTH][HEIGHT];
    public static int CONNECTED_USERS = 0;
    //public static List<Socket> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        gracze[0] = new Gracz();
        gracze[1] = new Gracz();
        gracze[2] = new Gracz();
        generateShips();
        for(int i = 0; i < HEIGHT; i++){
            for(int k = 0; k < WIDTH; k++){
                if(shipPlacement[k][i])
                    System.out.print('O');
                else
                    System.out.print(".");
            }
            System.out.println("");
        }
        // server is listening on port 5056
        ServerSocket ss = new ServerSocket(5056);
        Thread clientMessageHandler = new SerwerMessageHandler();
        clientMessageHandler.start();

        // running infinite loop for getting
        // client request
        while (true)
        {
            Socket s = null;

            try
            {
                // socket object to receive incoming client requests
                s = ss.accept();
                //clients.add(s);

                System.out.println("A new client is connected : " + s);
                if(CONNECTED_USERS >= MAX_PLAYERS ){
                    s.close();
                    System.out.println("Server is full");
                    continue;
                }

                // obtaining input and out streams
                gracze[CONNECTED_USERS].out = new ObjectOutputStream(s.getOutputStream());
                gracze[CONNECTED_USERS].in = new ObjectInputStream(s.getInputStream());
                gracze[CONNECTED_USERS].socket = s;
                gracze[CONNECTED_USERS].idGracza = s.getPort();


                System.out.println("Assigning new thread for this client");

                // create a new thread object

                Thread t = new SerwerClientHandler(s, gracze[CONNECTED_USERS].in, gracze[CONNECTED_USERS].out);
                CONNECTED_USERS++;

                // Invoking the start() method
                t.start();

            }
            catch (Exception e){
                s.close();
                CONNECTED_USERS--;
                System.out.println("USER DISCONNECTED - " + CONNECTED_USERS);
                e.printStackTrace();
            }
        }

    }


    private static void generateShips() {
        Random random_x = new Random();
        Random random_y = new Random();
        Random orient = new Random();
        for(int i = 0; i < MAX_PLAYERS; i++){
            boolean free = false;
            int shipSize = 5;
            for(int l = 0; l < 4;l++){
                boolean o = orient.nextBoolean();
                int x = random_x.nextInt(WIDTH);
                int y = random_y.nextInt(HEIGHT);
                if(o){  // horizontal
                    if(x + shipSize < WIDTH){
                        for(int k = 0; k < shipSize; k++){
                            if(!shipPlacement[x + k][y] && !free){
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
                                shipPlacement[x + k][y] = true;
                            }
                            //gracze[i].idGracza = gracze[i].socket.getPort();
                            gracze[i].stateks[l].start_x = x;
                            gracze[i].stateks[l].start_y = y;
                            gracze[i].stateks[l].end_x = x + shipSize;
                            gracze[i].stateks[l].end_y = y;
                            gracze[i].stateks[l].owner = gracze[i].idGracza;
                            gracze[i].stateks[l].size = shipSize;
                            gracze[i].stateks[l].sank = 0;
                            gracze[i].stateks[l].vertical=false;
                            shipSize--;
                        }
                    }
                    else{
                        l--;
                        continue;
                    }
                }
                else{   // vertical
                    if(y + shipSize < HEIGHT){
                        for(int k = 0; k < shipSize; k++){
                            if(!shipPlacement[x][y + k] && !free){
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
                                shipPlacement[x][y + k] = true;
                            }
                            //gracze[i].idGracza = gracze[i].socket.getPort();
                            gracze[i].stateks[l].start_x = x;
                            gracze[i].stateks[l].start_y = y;
                            gracze[i].stateks[l].end_x = x;
                            gracze[i].stateks[l].end_y = y + shipSize;
                            gracze[i].stateks[l].owner = gracze[i].idGracza;
                            gracze[i].stateks[l].size = shipSize;
                            gracze[i].stateks[l].sank = 0;
                            gracze[i].stateks[l].vertical=true;
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
