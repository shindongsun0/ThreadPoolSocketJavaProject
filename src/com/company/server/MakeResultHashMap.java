package com.company.server;

import java.util.HashMap;
import java.util.Map;

final class MakeResultHashMap {
    private Map<String, Integer> result;
    private MakeResultHashMap(){
        result = new HashMap<String, Integer>();
    }

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
