package com.company.server;

import java.util.HashMap;

public class LunchMenu {
    public HashMap<Integer, String> getLunchMenu(){
        MakeLunchMenuList list = new MakeLunchMenuList();
        return list.getLunchMenuList();
    }
}
