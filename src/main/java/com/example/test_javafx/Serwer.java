package com.example.test_javafx;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Serwer extends Thread{
    final static int WIDTH = 16;
    final static int HEIGHT = 9;
    final static int MAX_PLAYERS = 3;
    final static int PORT = 5555;
    public static Gracz gracze[] = new Gracz[MAX_PLAYERS];
    public static int tura;
    public static boolean hits[][] = new boolean[WIDTH][HEIGHT];
    public static boolean shipPlacement[][] = new boolean[WIDTH][HEIGHT];
    public static List<Socket> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        gracze[0] = new Gracz();
        gracze[1] = new Gracz();
        gracze[2] = new Gracz();
        generateShips();
        //displayShips();
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
        ServerSocket ss = new ServerSocket(PORT);
        Thread clientMessageHandler = new SerwerMessageHandler();
        clientMessageHandler.start();
        System.out.println("Listening on port: " + PORT);

        // running infinite loop for getting
        // client request
        while (true)
        {
            Socket s = null;
            try
            {
                if(clients.size() >= MAX_PLAYERS) {
                    System.out.println("Server is full!");
                    continue;
                }

                // socket object to receive incoming client requests
                s = ss.accept();
                clients.add(s);

                System.out.println("A new client is connected : " + s);

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // create a new thread object
                Thread t = new SerwerClientHandler(s, dis, dos);

                // Invoking the start() method
                t.start();

            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }

    }

    private static void displayShips() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int k = 0; k < WIDTH; k++) {
                if (shipPlacement[k][i]) {
                    for (int z = 0; z < MAX_PLAYERS; z++) {
                        for(int a = 0; a < 4; a++) {
                            if (!gracze[z].stateks[a].vertical && i >= gracze[z].stateks[a].start_y && i <= gracze[z].stateks[a].end_y && gracze[z].stateks[a].start_x == k)
                                System.out.print(gracze[z].idGracza);
                            else if (gracze[z].stateks[a].vertical && k >= gracze[z].stateks[a].start_x && k <= gracze[z].stateks[a].end_x && gracze[z].stateks[a].start_y == i)
                                System.out.print(gracze[z].idGracza);
                        }

                    }
                }
                else
                    System.out.print(".");
                }
                System.out.println("");
            }
        System.out.println("");
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
                if(o){  // vertical
                    if(x + shipSize < WIDTH){
                        for(int k = 0; k < shipSize; k++){
                            if(!shipPlacement[x + k][y]){
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
                            gracze[i].idGracza = i;
                            gracze[i].stateks[l].start_x = x;
                            gracze[i].stateks[l].start_y = y;
                            gracze[i].stateks[l].end_x = x + shipSize;
                            gracze[i].stateks[l].end_y = y;
                            gracze[i].stateks[l].owner = gracze[i].idGracza;
                            gracze[i].stateks[l].size = shipSize;
                            gracze[i].stateks[l].sank = 0;
                            gracze[i].stateks[l].vertical = true;
                            shipSize--;
                        }
                    }
                    else{
                        l--;
                        continue;
                    }
                }
                else{   // horizontal
                    if(y + shipSize < HEIGHT){
                        for(int k = 0; k < shipSize; k++){
                            if(!shipPlacement[x][y + k]){
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
                            gracze[i].idGracza = i;
                            gracze[i].stateks[l].start_x = x;
                            gracze[i].stateks[l].start_y = y;
                            gracze[i].stateks[l].end_x = x;
                            gracze[i].stateks[l].end_y = y + shipSize;
                            gracze[i].stateks[l].owner = gracze[i].idGracza;
                            gracze[i].stateks[l].size = shipSize;
                            gracze[i].stateks[l].sank = 0;
                            gracze[i].stateks[l].vertical = false;
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
