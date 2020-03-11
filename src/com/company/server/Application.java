package com.company.server;

public class Application {
    public static void main(String[] args){
        int poolNum = 20;
        int portNum = 10004;
        new ThreadPoolSocketServer(portNum, poolNum);
    }
}
