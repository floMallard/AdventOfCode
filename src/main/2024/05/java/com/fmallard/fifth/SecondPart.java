package com.fmallard.fifth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class SecondPart {
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
                if(currentLine[i] == 'A'){
                    result += checkXmas(table, lineIndex, i);
                }
            }
            lineIndex++;
        }
        System.out.println(result);
    }

    static int checkXmas(List<char[]> table, int lineIndex, int index) {
        if(lineIndex >= 1 && lineIndex <= table.size() - 2 && index >= 1 && index <= table.get(lineIndex).length - 2 ) {
            int count = 0;
            count += checkXmasUpDiagRight(table, lineIndex, index);
            count += checkXmasUpDiagLeft(table, lineIndex, index);
            count += checkXmasDownDiagLeft(table, lineIndex, index);
            count += checkXmasDownDiagRight(table, lineIndex, index);
            return count == 2 ? 1 : 0;
        }
        else
            return 0;
    }

    static int checkXmasUpDiagRight(List<char[]> table, int lineIndex, int index) {
        return table.get(lineIndex-1)[index+1] == 'M'
                && table.get(lineIndex+1)[index-1] == 'S' ? 1 : 0;
    }
    static int checkXmasUpDiagLeft(List<char[]> table, int lineIndex, int index) {
        return table.get(lineIndex-1)[index-1] == 'M'
                        && table.get(lineIndex+1)[index+1] == 'S' ? 1 : 0;
    }

    static int checkXmasDownDiagLeft(List<char[]> table, int lineIndex, int index) {
        return table.get(lineIndex+1)[index-1] == 'M'
                && table.get(lineIndex-1)[index+1] == 'S' ? 1 : 0;
    }
    static int checkXmasDownDiagRight(List<char[]> table, int lineIndex, int index) {
        return table.get(lineIndex+1)[index+1] == 'M'
                && table.get(lineIndex-1)[index-1] == 'S' ? 1 : 0;
    }
}