package com.fmallard.adventofcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Day07 {

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        long startTime = System.nanoTime();
        long result_p1 = 0;
        long result_p2 = 0;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("07/inputD7.txt");
        assert is != null;
        InputStreamReader streamReader = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(streamReader);

        for (String line; (line = reader.readLine()) != null;) {
            long expected = Long.parseLong(line.split(":")[0]);
            List<Integer> combinations = new ArrayList<>();
            String combinationsAsString = line.substring(line.indexOf(":")+2);
            for(String number : combinationsAsString.split(" ")) {
                combinations.add(Integer.parseInt(number));
            }
            boolean combinationFound_p1 = tryAllCombinations(expected, combinations, "*+");
            boolean combinationFound_p2 = tryAllCombinations(expected, combinations, "*+|");
            if(combinationFound_p1) {
                result_p1 += expected;
            }
            if(combinationFound_p2) {
                result_p2 += expected;
            }

        }

        long totalTime = (System.nanoTime() - startTime) / 1000000;
        System.out.printf("Results in %dms : p1=%d, p2=%d%n", totalTime, result_p1, result_p2);
    }

    private static boolean tryAllCombinations(long expected, List<Integer> combinations, String operators) {
        boolean found = false;
        Odo odo = new Odo(operators, combinations.size()-1);
        while(odo.hasNext && !found) {
            String next = odo.emitNext();
            long calculation = combinations.get(0);
            for(int i = 1; i < combinations.size(); i++) {
                if(next.charAt(i-1) == '+') {
                    calculation += combinations.get(i);
                } else if(next.charAt(i-1) == '*'){
                    calculation *= combinations.get(i);
                } else {
                    String calcAsString = calculation + Long.toString(combinations.get(i));
                    calculation = Long.parseLong(calcAsString);
                }
            }
            found = calculation == expected;
        }
        return found;
    }
    static class Odo {
        private final char [] chars;
        private final int [] positions;
        private boolean hasNext;

        Odo(String chars, int nPositions) {
            this.chars = chars.toCharArray();
            this.positions = new int [nPositions];
            this.hasNext = true;
        }

        boolean hasNext() {
            return hasNext;
        }

        String emitNext() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < positions.length; ++i) sb.append(chars[positions[i]]);
            for (int i = 0; i < positions.length; ++i) {
                if (++positions[i] < chars.length) {
                    hasNext = true;
                    return sb.toString();
                }
                positions[i] = 0;
            }
            hasNext = false;
            return sb.toString();
        }
    }
}