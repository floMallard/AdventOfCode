package com.fmallard.adventofcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day01 {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        long startTime = System.nanoTime();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("01/input.txt");
        assert is != null;
        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        List<Integer> col1 = new ArrayList<>();
        List<Integer> col2 = new ArrayList<>();
        for (String line; (line = reader.readLine()) != null;) {
            String[] split = line.split("(\\s+)");
            col1.add(Integer.parseInt(split[0]));
            col2.add(Integer.parseInt(split[1]));
        }
        Collections.sort(col1);
        Collections.sort(col2);

        int result_p1 = 0;
        for(int i = 0; i < col1.size(); i++) {
            result_p1 += Math.abs(col1.get(i) - col2.get(i));
        }
        int result_p2 = 0;
        for (int next : col1) {
            long occurences = col2.stream().filter(n -> n.equals(next)).count();
            result_p2 += (int) (next * occurences);
        }
        long totalTime = (System.nanoTime() - startTime) / 1000000;
        System.out.printf("Results in %dms : p1=%d, p2=%d%n", totalTime, result_p1, result_p2);
    }
}