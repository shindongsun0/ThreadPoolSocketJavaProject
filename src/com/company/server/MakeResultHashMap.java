package com.company.server;

import java.util.HashMap;
import java.util.Map;

final class MakeResultHashMap {
    private Map<String, Integer> result= new HashMap<String, Integer>();
    private MakeResultHashMap(){}

    @Override
    protected void finalize() throws Throwable{
        System.out.println("싱글톤 삭제");
    }

    public static MakeResultHashMap getInstance(){
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder{
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
