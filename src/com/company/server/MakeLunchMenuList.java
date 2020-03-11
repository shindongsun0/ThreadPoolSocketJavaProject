package com.company.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class MakeLunchMenuList {
    final HashMap<Integer, String> menus = new HashMap<>();

    String fileName = System.getProperty("user.dir") + "/makeMenu.txt";

    MakeLunchMenuList() {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))) {
            String s = null;
            int i = 1;

            while ((s = fileReader.readLine()) != null) {
                menus.put(i++, s);
            }
        } catch (IOException e) {
            System.out.println(e.toString());
            System.out.println(String.format("%s OCCURRED", e.getClass().getSimpleName()));
            System.out.println(Arrays.asList(e.getStackTrace()));
        }
    }

    public HashMap<Integer, String> getLunchMenuList(){
        return this.menus;
    }

}
