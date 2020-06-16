package com.sebi.TcpServer;

import com.sebi.TcpServer.ClientThread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TcpServer {
    ServerSocket serverSocket;
    ArrayList<Socket> clientsSocket = new ArrayList<Socket>();
    ArrayList<ClientThread> clientsThread = new ArrayList<ClientThread>();

    public void startServer(){
        try {
            this.serverSocket = new ServerSocket(1111);
            while(true){
                Socket clientSocket = serverSocket.accept();
                this.clientsSocket.add(clientSocket);
                System.out.println("client sensor connected");

                ClientThread clientThread = new ClientThread(clientSocket);
                this.clientsThread.add(clientThread);
                clientThread.start();

            }

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void stopServer(){
        try {
            this.serverSocket.close();
            for(ClientThread ct : this.clientsThread){
                ct.stop();
            }
            for(Socket s : this.clientsSocket){
                s.close();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}
