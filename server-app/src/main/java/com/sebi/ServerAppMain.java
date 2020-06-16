package com.sebi;

import com.sebi.TcpServer.TcpServer;
import com.sebi.service.ServerService;

public class ServerAppMain {
    public static void main(String[] args){
        System.out.println("Server started!");
        //TcpServer server = new TcpServer();
        //server.startServer();
        ServerService ss = new ServerService();
        ss.add("s1",10, 1000);
    }
}
