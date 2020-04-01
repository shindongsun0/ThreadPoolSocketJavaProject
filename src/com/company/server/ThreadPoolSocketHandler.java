package com.company.server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;

public class ThreadPoolSocketHandler implements Runnable{
    Socket clientSocket;
    VoteManager voteManager;
    PrintWriter printWriter;
    BufferedReader reader;
    InputStream inputStream;
    OutputStream outputStream;

    public ThreadPoolSocketHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        InetAddress inetAddress = clientSocket.getInetAddress();
        System.out.println(inetAddress.getHostAddress() + " 로부터 접속했습니다.");
    }

    public void connectStream(){
        try {
            this.inputStream = clientSocket.getInputStream();
            this.reader = new BufferedReader(new InputStreamReader(inputStream));

            this.outputStream = clientSocket.getOutputStream();
            this.printWriter = new PrintWriter(new OutputStreamWriter(outputStream), true);
        }catch(IOException e) {
            System.out.println(e.toString());
            System.out.println(Arrays.asList(e.getStackTrace()));
        }
    }

    public void tryShutDown(){
        try {
            this.clientSocket.close();
            this.reader.close();
            this.printWriter.close();
        }catch(IOException e) {
            System.out.println(e.toString());
            System.out.println(Arrays.asList(e.getStackTrace()));
        }
    }

    public void printMenus(){
        voteManager = new VoteManager(MakeResultHashMap.getInstance().getVoteResultMap());
        voteManager.printMenus();
    }

    public void readFromClient(BufferedReader reader) {
        String number = null;
        try {
            while (true) {
                if (!((number = reader.readLine()) != null && !number.equals("quit")))
                    break;
                else
                    voteManager.countVoteResult(number);
            }
        }catch (IOException e) {
            System.out.println(e.toString());
            System.out.println(Arrays.asList(e.getStackTrace()));
        }
        voteManager.announceRecentResult();
    }

    @Override
    public void run(){
        connectStream();
        printMenus();
        readFromClient(this.reader);
        tryShutDown();
    }
}
