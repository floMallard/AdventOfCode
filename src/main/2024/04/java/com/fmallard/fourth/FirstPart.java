package com.fmallard.fourth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class FirstPart {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        int result = 0;
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
                    result += checkXmas(table, lineIndex, i);
                    result += checkXmasBackwards(table, lineIndex, i);
                    result += checkXmasUp(table, lineIndex, i);
                    result += checkXmasUpDiagRight(table, lineIndex, i);
                    result += checkXmasUpDiagLeft(table, lineIndex, i);
                    result += checkXmasDown(table, lineIndex, i);
                    result += checkXmasDownDiagLeft(table, lineIndex, i);
                    result += checkXmasDownDiagRight(table, lineIndex, i);
                }
            }
            lineIndex++;
        }
        System.out.println(result);
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
}