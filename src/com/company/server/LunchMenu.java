package com.company.server;

import java.util.Map;

public class LunchMenu {
    public Map<Integer, String> getLunchMenu(){
        String fileName = System.getProperty("user.dir") + "/makeMenu.txt";

        MakeLunchMenuMap lunchMenuMap = new MakeLunchMenuMap(fileName);
        return lunchMenuMap.getLunchMenuList();
    }
}
