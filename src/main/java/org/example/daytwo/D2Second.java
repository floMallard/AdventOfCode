package org.example.daytwo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class D2Second {

    public static void main(String[] args) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("daytwo/inputD2.txt");
        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        int minIncrement = 1;
        int maxIncrement = 3;
        int result = 0;
        for (String line; (line = reader.readLine()) != null;) {
            String[] split = line.split("(\\s+)");

            List<Integer> numberInReport = new ArrayList<>();
            for(String s : split) {
                numberInReport.add(Integer.valueOf(s));
            }
            boolean increase = isSafe(numberInReport, minIncrement, maxIncrement);
            for(int i = 0; i < numberInReport.size(); i++) {
                List<Integer> withRemoved = new ArrayList<>(numberInReport);
                withRemoved.remove(i);
                increase = increase || isSafe(withRemoved, minIncrement, maxIncrement);
            }
            if(increase) result++;
        }
        System.out.println(result);
    }

    private static boolean isSafe(List<Integer> numberInReport, int minIncrement, int maxIncrement) {
        boolean increase = true;
        boolean increasing = numberInReport.get(0) < numberInReport.get(1);
        for(int i = 0; i < numberInReport.size()-1; i++) {
            if(increasing) {
                if(numberInReport.get(i + 1) < numberInReport.get(i) + minIncrement || numberInReport.get(i + 1) > numberInReport.get(i) + maxIncrement) {
                        increase = false;
                        break;
                }
            }
            else {
                if(numberInReport.get(i + 1) > numberInReport.get(i) - minIncrement || numberInReport.get(i + 1) < numberInReport.get(i) - maxIncrement) {
                        increase = false;
                        break;
                }
            }
        }
        return increase;
    }
}