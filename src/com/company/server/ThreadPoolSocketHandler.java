package com.company.server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;

public class ThreadPoolSocketHandler implements Runnable{
    private Socket clientSocket;
    VoteManager voteManager;
    PrintWriter printWriter;
    BufferedReader reader;

    public ThreadPoolSocketHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        InetAddress inetAddress = clientSocket.getInetAddress();
        System.out.println(inetAddress.getHostAddress() + " 로부터 접속했습니다.");
    }

    public void connectStream(){
        try {
            InputStream inputStream = clientSocket.getInputStream();
            this.reader = new BufferedReader(new InputStreamReader(inputStream));

            OutputStream outputStream = clientSocket.getOutputStream();
            this.printWriter = new PrintWriter(new OutputStreamWriter(outputStream), true);
        }catch(IOException e) {
            System.out.println(e.toString());
            System.out.println(Arrays.asList(e.getStackTrace()));
        }
    }

    public void tryShutDown(){
        try {
            clientSocket.close();
            reader.close();
            printWriter.close();
        }catch(IOException e) {
            System.out.println(e.toString());
            System.out.println(Arrays.asList(e.getStackTrace()));
        }
    }

    public void printMenus(){
        voteManager = new VoteManager(MakeResultHashMap.getInstance().getVoteResultMap());
        voteManager.printMenus();
    }

    public void readFromClient(){
        String number = null;
        while (true) {
            try {
                if (!((number = reader.readLine()) != null && !number.equals("quit")))
                    break;
            } catch (IOException e) {
                System.out.println(e.toString());
                System.out.println(Arrays.asList(e.getStackTrace()));
            }
            System.out.println(number);
            voteManager.countVoteResult(number);
        }
        voteManager.broadcast();
    }

    @Override
    public void run(){
        connectStream();
        printMenus();
        readFromClient();
        tryShutDown();
    }
}
