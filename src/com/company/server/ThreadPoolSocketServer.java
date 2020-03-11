package com.company.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolSocketServer{
    public ThreadPoolSocketServer(int portNum, int poolNum){
        try {
            ServerSocket serverSocket = new ServerSocket(portNum);
            ExecutorService executorService = Executors.newFixedThreadPool(poolNum);
            setMenuAndResult();

            while (!executorService.isShutdown()) {
                executorService.execute(new ThreadPoolSocketHandler(serverSocket.accept()));
            }
        } catch (IOException e) {
            System.out.println(e.toString());
            System.out.println(String.format("%s OCCURRED", e.getClass().getSimpleName()));
            System.out.println(Arrays.asList(e.getStackTrace()));
        }
    }
    private void setMenuAndResult(){
        LunchMenu menu = new LunchMenu();
        MakeResultHashMap.getInstance().setVoteResult(menu.getLunchMenu());
    }
}
