package com.example.test_javafx;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class SerwerResponseHandler extends Thread {
    final ObjectInputStream dis;
    //final DataOutputStream dos;
    final Socket s;

    public SerwerResponseHandler(ObjectInputStream dis, Socket s) {
        this.dis = dis;
        this.s = s;
    }
    boolean checkHits(int x,int y){
        for (int i = 0; i < Serwer.CONNECTED_USERS; i++) {
            for (int j = 0; j < 4; j++) {
                if (!Serwer.gracze[i].stateks[j].vertical)
                {
                    for (int k = Serwer.gracze[i].stateks[j].start_x; k < Serwer.gracze[i].stateks[j].end_x; k++) {
                        if(x==k&&y==Serwer.gracze[i].stateks[j].start_y)
                            return true;

                    }
                }
                else
                {
                    for (int k = Serwer.gracze[i].stateks[j].start_y; k < Serwer.gracze[i].stateks[j].end_y; k++) {
                        if(y==k&&x==Serwer.gracze[i].stateks[j].start_x)
                            return true;

                    }
                }
            }
        }
        return false;
    }

    public void run() {
        String received;
        String timestamp;

        while (true) {
            try {
                ClientDataPackege obj = (ClientDataPackege) dis.readObject();
                //System.out.print("DEBUG");
                Serwer.hits[obj.x][obj.y]=true;
                System.out.println(obj.x + ", " + obj.y);
                if(!checkHits(obj.x,obj.y))
                    Serwer.tura++;
                /*if(received.equals("Exit"))
                {
                    System.out.println("Closing this connection : " + s);
                    s.close();
                    System.out.println("Connection closed");
                    break;
                }*/

                //System.out.println(timestamp + "Klient " + s.getPort() + " : " + received);

            } catch (IOException e) {
                e.printStackTrace();
                try {
                    s.close();
                    Serwer.CONNECTED_USERS--;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }
}
