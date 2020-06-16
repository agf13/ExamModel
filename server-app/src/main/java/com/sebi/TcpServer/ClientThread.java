package com.sebi.TcpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.Thread;
import java.net.Socket;
import java.util.Random;

public class ClientThread implements Runnable{
    Socket socket;
    Thread clientThread;

    BufferedReader clientReader;

    Random rand;
    int maximumSleepTime = 1000;

    boolean working;

    public ClientThread(Socket clientSocket){
        try {
            this.socket = clientSocket;
            this.clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            rand = new Random();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        while(this.working == true) {
            try {
                String message = this.clientReader.readLine();
                System.out.println(message);
                Thread.sleep(rand.nextInt(this.maximumSleepTime));
            } catch (IOException e) {
                e.printStackTrace();
                this.working = false;
            }
            catch (InterruptedException e){
                e.printStackTrace();
                this.working = false;
            }
        }
    }

    public void stop(){
        this.working = false;
        this.clientThread.interrupt();
    }

    public void start(){
        this.working = true;
        this.clientThread = new Thread(this::run);
        this.clientThread.start();
    }
}
