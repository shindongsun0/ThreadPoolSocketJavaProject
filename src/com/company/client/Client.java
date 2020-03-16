package com.company.client;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

public class Client {

    private static String findMenuFile(){
        return System.getProperty("user.dir") + "/showMenu.txt";
    }

    private static void readFileData(){
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(findMenuFile()));
            String s = null;

            while((s = fileReader.readLine())!= null){
                System.out.println(s);
            }
        } catch (IOException e) {
            System.out.println(e.toString());
            System.out.println(String.format("%s OCCURRED", e.getClass().getSimpleName()));
            System.out.println(Arrays.asList(e.getStackTrace()));
        }
    }

    private static void printSocketBuffer(Socket socket) {
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = null;
        BufferedReader br = null;
        try {
            OutputStream out = socket.getOutputStream();
            pw = new PrintWriter(new OutputStreamWriter(out), true);

            String line = null;

            while((line = keyboard.readLine()) != null && !line.equals("quit")) {
                System.out.println("내가 보낸 메뉴 번호: " + line);
                pw.println(line);
            }

            System.out.println("socket 연결 종료");
        }catch(IOException e) {
            System.out.println(e.toString());
            System.out.println(String.format("%s OCCURRED", e.getClass().getSimpleName()));
            System.out.println(Arrays.asList(e.getStackTrace()));
        } finally {
            try {
                keyboard.close();
                pw.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Socket connectSocket(String hostname, int port, int timeout){
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(hostname, port), timeout);
            return socket;
        }catch(IOException e){
            System.out.println(e.toString());
            System.out.println(String.format("%s OCCURRED", e.getClass().getSimpleName()));
            System.out.println(Arrays.asList(e.getStackTrace()));
            return null;
        }
    }

    public static void main(String[] args){
        Socket socket = connectSocket("localhost", 10004, 5000);
        readFileData();
        printSocketBuffer(socket);
    }

}
