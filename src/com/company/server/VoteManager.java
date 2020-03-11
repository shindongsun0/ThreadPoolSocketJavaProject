package com.company.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class VoteManager{
    final Map<String, Integer> result;
    VoteManager(Map<String, Integer> result){
        this.result = result;
    }


    protected void countVoteResult(String number) {
        result.computeIfPresent(number, (k, v) -> v + 1);
        System.out.println("현재 " + number +"는 " + result.get(number) + "입니다.");
    }

    public void broadcast(){
        Iterator<String> keys = result.keySet().iterator();
        LinkedList<Integer> lunch = new LinkedList<>();
        LinkedList<String> lunchMenu = new LinkedList<>();
        int i = 0;
        lunch.add(0);
        while(keys.hasNext()) {
            String key = keys.next();
            if (lunch.get(i) < result.get(key)) {
                lunch.remove(0);
                lunch.add(result.get(key));
                lunchMenu.add(key);
            }
            else if(lunch.get(i).equals(result.get(key))){
                lunch.add(result.get(key));
                lunchMenu.add(key);
                i++;
            }
        }

        StringBuilder result = null;
        if(lunch.size() > 1){
            result = new StringBuilder("지금까지 1위는 공동입니다.");
            for(int j =0; j < lunch.size(); j++){
                result.append("메뉴는 ").append(lunchMenu.get(j)).append(", 득표수는 ").append(lunch.get(j)).append("입니다.");
            }
        }
        else {
            result = new StringBuilder("지금까지 1위는 " + lunchMenu.get(0) + ", 득표수는 " + lunch.get(0) + "입니다.");
        }
        System.out.println(result);
    }

    public void printMenus(){
        String fileName = System.getProperty("user.dir") + "/showMenu.txt";
        LunchMenu menu = new LunchMenu();
        Set<Map.Entry<Integer, String>> set = menu.getLunchMenu().entrySet();
        Iterator<Map.Entry<Integer, String>> itr = set.iterator();

        try (PrintWriter filePrintWriter = new PrintWriter(fileName)) {
            filePrintWriter.println("===============메뉴==============");
            while(itr.hasNext()){
                Map.Entry<Integer, String> e = (Map.Entry<Integer, String>)itr.next();
                filePrintWriter.println("번호: " + e.getKey() + ", 메뉴: " + e.getValue());
            }
            filePrintWriter.println("================================");
            filePrintWriter.println("종료를 원하시면 'quit'를 입력해주세요!");
            filePrintWriter.println("먹고 싶은 메뉴의 번호를 연타해주세요!");
        }catch (IOException e){
            System.out.println(e.toString());
            System.out.println(String.format("%s OCCURRED", e.getClass().getSimpleName()));
            System.out.println(Arrays.asList(e.getStackTrace()));
        }
    }
}
