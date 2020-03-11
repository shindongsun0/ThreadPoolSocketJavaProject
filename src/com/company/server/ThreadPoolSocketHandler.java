package com.company.server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;

public class ThreadPoolSocketHandler implements Runnable{
    private Socket clientSocket;
    VoteManager voteManager;
    PrintWriter printWriter;

    public ThreadPoolSocketHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        InetAddress inetAddress = clientSocket.getInetAddress();
        System.out.println(inetAddress.getHostAddress() + " 로부터 접속했습니다.");
    }


    @Override
    public void run(){
        try{
            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            OutputStream outputStream = clientSocket.getOutputStream();
            printWriter = new PrintWriter(new OutputStreamWriter(outputStream), true);

            voteManager = new VoteManager(MakeResultHashMap.getInstance().getVoteResultMap());
            voteManager.printMenus();

            String number = null;
            while ((number = reader.readLine()) != null && !number.equals("quit")) {
                System.out.println(number);
                voteManager.countVoteResult(number);
            }
            voteManager.broadcast();

            clientSocket.close();
            reader.close();
            printWriter.close();
        } catch(IOException e) {
            System.out.println(e.toString());
            System.out.println(String.format("%s OCCURRED", e.getClass().getSimpleName()));
            System.out.println(Arrays.asList(e.getStackTrace()));
        }
    }
}
