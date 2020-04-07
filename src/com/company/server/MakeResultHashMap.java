package com.company.server;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MakeResultHashMap {
    private Map<String, Integer> result = new ConcurrentHashMap<String, Integer>();
    private MakeResultHashMap(){}

    public static MakeResultHashMap getInstance(){
        return ResultInstance.INSTANCE;
    }

    private static class ResultInstance{
        private static final MakeResultHashMap INSTANCE = new MakeResultHashMap();
    }

    public void setVoteResult(Map<Integer, String> menus){
        for(Integer menu : menus.keySet()){
            result.put(menu.toString(), 0);
        }
    }

    public Map<String, Integer> getVoteResultMap(){
        return this.result;
    }
}
