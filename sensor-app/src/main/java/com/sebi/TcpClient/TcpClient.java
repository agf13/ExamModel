package com.sebi.TcpClient;

import java.io.*;
import java.net.Socket;

public class TcpClient {
    Socket clientSocket;
    PrintWriter os;
    public void startClient() throws  IOException{
        try {
            this.clientSocket = new Socket("localhost", 1111);
            System.out.println("Client sensor connected to server");
            this.os = new PrintWriter(clientSocket.getOutputStream());
        }
        catch(IOException e){
            throw  new IOException("Sensor could not connect to server");
            //System.out.println("Sensor could not connect to server");
            //e.printStackTrace();
        }
    }

    public void closeClient(){
        try {
            clientSocket.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        os.println(message);
        os.flush();
    }
}
