package org.boluanace;

import config.JavaConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.*;

/**
 * 斗地主:从一副牌中选五张，判断牌型（同花顺、四条等等）
 */

@Component
public class DouDiZhu {

    private ArrayList<Pair> arrayList = new ArrayList<>();
    private HashMap<String, Integer> colorMap = new HashMap<>();
    private HashMap<Integer, Integer> numMap = new HashMap<>();
    private InputStream inputStream;

    public static class Pair {

        private int first;
        private String second;

        public Pair(int first, String second) {
            this.first = first;
            this.second = second;
        }

        public int getFirst() {
            return first;
        }

        public String getSecond() {
            return second;
        }
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
        DouDiZhu dou = (DouDiZhu) context.getBean("DouDiZhu");
        dou.setInputStream(System.in);
        System.out.println(dou.getResult());
    }


    public void getHashMap() {
        for (Pair p : this.arrayList) {
            if (!this.colorMap.containsKey(p.getSecond())) {
                this.colorMap.put(p.getSecond(), 1);
            } else this.colorMap.put(p.getSecond(), this.colorMap.get(p.getSecond()) + 1);

            if (!this.numMap.containsKey(p.getFirst())) {
                this.numMap.put(p.getFirst(), 1);
            } else this.numMap.put(p.getFirst(), this.numMap.get(p.getFirst()) + 1);
        }
    }


    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        Scanner scanner = new Scanner(this.inputStream);
        for(int i = 0; i < 5; i++){
            int s1 = scanner.nextInt();
            String s2 = scanner.next();
            Pair pair = new Pair(s1, s2);
            this.arrayList.add(pair);
        }
        scanner.close();
        getHashMap();
    }

    /**
     * 判断是否为四条
     *
     * @return
     */
    public boolean isSiTiao() {
        if (this.numMap.size() == 2) {
            Integer[] ar = numMap.keySet().toArray(new Integer[0]);
            return (numMap.get(ar[0]) == 4 && numMap.get(ar[1]) == 1) || (numMap.get(ar[0]) == 1 && numMap.get(ar[1]) == 4);
        }
        return false;
    }

    /**
     * 判断是否为同花
     *
     * @return
     */
    public boolean isTongHua() {
        return colorMap.keySet().size() == 1;
    }

    /**
     * 判断是否为顺子
     *
     * @return
     */
    public boolean isShunZi() {
        arrayList.sort(Comparator.comparingInt(Pair::getFirst));
//        for(Pair p : arrayList) {
//            System.out.print(p.getFirst() + " ");
//        }
        for (int i = 1; i < arrayList.size(); i++) {
            if (arrayList.get(i).getFirst() - arrayList.get(i - 1).getFirst() != 1)
                return false;
        }
        return true;
    }

    /**
     * 判断是否为同花顺
     *
     * @return
     */
    public boolean isTongHuaShun() {
        return isTongHua() && isShunZi();
    }

    /**
     * 判断是否为葫芦
     *
     * @return
     */
    public boolean isHULu() {
        if (this.numMap.size() == 2) {
            Integer[] ar = numMap.keySet().toArray(new Integer[0]);
            return (numMap.get(ar[0]) == 3 && numMap.get(ar[1]) == 2) || (numMap.get(ar[0]) == 2 && numMap.get(ar[1]) == 3);
        }
        return false;
    }

    /**
     * 判断是否为三条
     *
     * @return
     */
    public boolean isSanTiao() {
        if (this.numMap.size() == 3) {
            Integer[] ar = numMap.keySet().toArray(new Integer[0]);
            return ar[0] == 3 || ar[1] == 3 || ar[2] == 3;
        }
        return false;
    }

    /**
     *
     * @return 1表示同花顺，2表示四条，3表示葫芦，4表示同花，5表示顺子，6表示三条，7表示其他
     */
    public int getResult() {
        int result;
        if (isTongHuaShun())
            result = 1;
        else if (isSiTiao())
            result = 2;
        else if (isHULu())
            result = 3;
        else if (isTongHua())
            result = 4;
        else if (isShunZi())
            result = 5;
        else if (isSanTiao())
            result = 6;
        else result = 7;
        return result;
    }
}




