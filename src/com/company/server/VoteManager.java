package com.company.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class VoteManager{
    final Map<String, Integer> result;
    VoteManager(Map<String, Integer> result){
        this.result = result;
    }

    public void countVoteResult(String number) {
        result.computeIfPresent(number, (k, v) -> v + 1);
        System.out.println("현재 " + number +"는 " + result.get(number) + "입니다.");
    }

    private void countLunchMenu(List<Integer> lunch, List<String> lunchMenu){
        Iterator<String> keys = result.keySet().iterator();
        int i = 0;
        lunch.add(0);
        lunchMenu.add("");
        while(keys.hasNext()) {
            String key = keys.next();
            if (lunch.get(i) < result.get(key)) {
                lunch.remove(0);
                lunch.add(result.get(key));
                lunchMenu.remove(0);
                lunchMenu.add(key);
            }
            else if(lunch.get(i).equals(result.get(key))){
                lunch.add(result.get(key));
                lunchMenu.add(key);
                i++;
            }
        }
    }

    public void announceRecentResult(){
        List<Integer> lunch = new LinkedList<>();
        List<String> lunchMenu = new LinkedList<>();
        countLunchMenu(lunch, lunchMenu);

        StringBuilder result = null;
        if(lunch.size() > 1){
            result = new StringBuilder("지금까지 1위는 공동입니다.\n");
            for(int j =0; j < lunch.size(); j++){
                result.append("메뉴는 ").append(lunchMenu.get(j)).append(", 득표수는 ").append(lunch.get(j)).append("입니다. ");
            }
        }
        else {
            result = new StringBuilder("지금까지 1위는 " + lunchMenu.get(0) + ", 득표수는 " + lunch.get(0) + "입니다.");
        }
        System.out.println(result);
    }

    public Iterator<Map.Entry<Integer, String>> setMenuIterator(){
        LunchMenu menu = new LunchMenu();
        Set<Map.Entry<Integer, String>> set = menu.getLunchMenu().entrySet();
        Iterator<Map.Entry<Integer, String>> itr = set.iterator();
        return itr;
    }
    public void printMenus(){
        Iterator<Map.Entry<Integer, String>> itr = setMenuIterator();

        String fileName = System.getProperty("user.dir") + "/src/showMenu.txt";
        try (PrintWriter filePrintWriter = new PrintWriter(fileName)) {
            filePrintWriter.println("===============메뉴==============");
            while(itr.hasNext()){
                Map.Entry<Integer, String> e = itr.next();
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
