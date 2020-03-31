package com.company.server;

import java.util.Map;

public class LunchMenu {
    public Map<Integer, String> getLunchMenu(){
        MakeLunchMenuList list = new MakeLunchMenuList();
        return list.getLunchMenuList();
    }
}
