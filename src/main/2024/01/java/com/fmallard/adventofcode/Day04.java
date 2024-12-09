package com.fmallard.adventofcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Day04 {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        long startTime = System.nanoTime();
        int result_p1 = 0;
        int result_p2 = 0;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("04/inputD4.txt");
        InputStreamReader streamReader = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(streamReader);
        List<char[]> table = new ArrayList<>();

        for (String line; (line = reader.readLine()) != null;) {
            table.add(line.toCharArray());
        }
        int lineIndex = 0;
        for(char[] currentLine : table) {
            for(int i = 0; i < currentLine.length; i++) {
                if(currentLine[i] == 'X'){
                    result_p1 += checkXmas(table, lineIndex, i);
                    result_p1 += checkXmasBackwards(table, lineIndex, i);
                    result_p1 += checkXmasUp(table, lineIndex, i);
                    result_p1 += checkXmasUpDiagRight(table, lineIndex, i);
                    result_p1 += checkXmasUpDiagLeft(table, lineIndex, i);
                    result_p1 += checkXmasDown(table, lineIndex, i);
                    result_p1 += checkXmasDownDiagLeft(table, lineIndex, i);
                    result_p1 += checkXmasDownDiagRight(table, lineIndex, i);
                }
            }
            lineIndex++;
        }
        lineIndex = 0;
        for(char[] currentLine : table) {
            for(int i = 0; i < currentLine.length; i++) {
                if(currentLine[i] == 'A'){
                    result_p2 += checkXmasP2(table, lineIndex, i);
                }
            }
            lineIndex++;
        }
        long totalTime = (System.nanoTime() - startTime) / 1000000;
        System.out.printf("Results in %dms : p1=%d, p2=%d%n", totalTime, result_p1, result_p2);
    }

    static int checkXmas(List<char[]> table, int lineIndex, int index) {
        return index < table.get(lineIndex).length - 3 &&
                table.get(lineIndex)[index+1] == 'M'
                && table.get(lineIndex)[index+2] == 'A'
                && table.get(lineIndex)[index+3] == 'S' ? 1 : 0;
    }
    static int checkXmasBackwards(List<char[]> table, int lineIndex, int index) {
        return index >= 3 &&
                table.get(lineIndex)[index-1] == 'M'
                && table.get(lineIndex)[index-2] == 'A'
                && table.get(lineIndex)[index-3] == 'S' ? 1 : 0;
    }
    static int checkXmasUp(List<char[]> table, int lineIndex, int index) {
        return lineIndex >= 3
                && table.get(lineIndex-1)[index] == 'M'
                && table.get(lineIndex-2)[index] == 'A'
                && table.get(lineIndex-3)[index] == 'S' ? 1 : 0;
    }
    static int checkXmasUpDiagRight(List<char[]> table, int lineIndex, int index) {
        return lineIndex >= 3 && index < table.get(lineIndex).length - 3
                && table.get(lineIndex-1)[index+1] == 'M'
                && table.get(lineIndex-2)[index+2] == 'A'
                && table.get(lineIndex-3)[index+3] == 'S' ? 1 : 0;
    }
    static int checkXmasUpDiagLeft(List<char[]> table, int lineIndex, int index) {
        return lineIndex >= 3 && index >= 3
                && table.get(lineIndex-1)[index-1] == 'M'
                && table.get(lineIndex-2)[index-2] == 'A'
                && table.get(lineIndex-3)[index-3] == 'S' ? 1 : 0;
    }
    static int checkXmasDown(List<char[]> table, int lineIndex, int index) {
        return lineIndex < table.size() - 3
                && table.get(lineIndex+1)[index] == 'M'
                && table.get(lineIndex+2)[index] == 'A'
                && table.get(lineIndex+3)[index] == 'S' ? 1 : 0;
    }
    static int checkXmasDownDiagLeft(List<char[]> table, int lineIndex, int index) {
        return lineIndex < table.size() - 3 && index >= 3
                && table.get(lineIndex+1)[index-1] == 'M'
                && table.get(lineIndex+2)[index-2] == 'A'
                && table.get(lineIndex+3)[index-3] == 'S' ? 1 : 0;
    }
    static int checkXmasDownDiagRight(List<char[]> table, int lineIndex, int index) {
        return lineIndex < table.size() - 3 && index < table.get(lineIndex).length - 3
                && table.get(lineIndex+1)[index+1] == 'M'
                && table.get(lineIndex+2)[index+2] == 'A'
                && table.get(lineIndex+3)[index+3] == 'S' ? 1 : 0;
    }

    static int checkXmasP2(List<char[]> table, int lineIndex, int index) {
        if(lineIndex >= 1 && lineIndex <= table.size() - 2 && index >= 1 && index <= table.get(lineIndex).length - 2 ) {
            int count = 0;
            count += checkXmasUpDiagRightP2(table, lineIndex, index);
            count += checkXmasUpDiagLeftP2(table, lineIndex, index);
            count += checkXmasDownDiagLeftP2(table, lineIndex, index);
            count += checkXmasDownDiagRightP2(table, lineIndex, index);
            return count == 2 ? 1 : 0;
        }
        else
            return 0;
    }

    static int checkXmasUpDiagRightP2(List<char[]> table, int lineIndex, int index) {
        return table.get(lineIndex-1)[index+1] == 'M'
                && table.get(lineIndex+1)[index-1] == 'S' ? 1 : 0;
    }
    static int checkXmasUpDiagLeftP2(List<char[]> table, int lineIndex, int index) {
        return table.get(lineIndex-1)[index-1] == 'M'
                && table.get(lineIndex+1)[index+1] == 'S' ? 1 : 0;
    }

    static int checkXmasDownDiagLeftP2(List<char[]> table, int lineIndex, int index) {
        return table.get(lineIndex+1)[index-1] == 'M'
                && table.get(lineIndex-1)[index+1] == 'S' ? 1 : 0;
    }
    static int checkXmasDownDiagRightP2(List<char[]> table, int lineIndex, int index) {
        return table.get(lineIndex+1)[index+1] == 'M'
                && table.get(lineIndex-1)[index-1] == 'S' ? 1 : 0;
    }
}