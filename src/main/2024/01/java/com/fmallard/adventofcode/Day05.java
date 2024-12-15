package com.fmallard.adventofcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day05 {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        long startTime = System.nanoTime();
        int result_p1 = 0;
        int result_p2 = 0;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("05/input.txt");
        assert is != null;
        InputStreamReader streamReader = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(streamReader);
        List<List<Integer>> rules = new ArrayList<>();
        List<List<Integer>> updates = new ArrayList<>();
        for (String line; (line = reader.readLine()) != null;) {
            if(line.contains("|")) {
                List<Integer> numbers = Arrays.stream(line.split("\\|")).map(Integer::parseInt).toList();
                rules.add(numbers);
            } else if (!line.isEmpty()) {
                List<Integer> update = Arrays.stream(line.split(",")).map(Integer::parseInt).toList();
                updates.add(update);
            }
        }

        for(List<Integer> update : updates) {
            boolean isCorrect = true;
            for(List<Integer> rule : rules) {
                int firstRuleIndex = update.indexOf(rule.get(0));
                int secondRuleIndex = update.indexOf(rule.get(1));
                if(firstRuleIndex != -1 && secondRuleIndex != -1 && firstRuleIndex > secondRuleIndex) {
                    isCorrect = false;
                }
            }
            List<Integer> correct = new ArrayList<>(update.size());
            correct.addAll(update);
            if (isIncorrect(update, rules)) {
                while (isIncorrect(correct, rules)) {
                    swapIncorrect(correct, rules);
                }
                result_p2 += correct.get((int) (double) (correct.size() / 2));
            }
            if(isCorrect) {
                result_p1 += update.get((int) (double) (update.size() / 2));
            }
        }
        long totalTime = (System.nanoTime() - startTime) / 1000000;
        System.out.printf("Results in %dms : p1=%d, p2=%d%n", totalTime, result_p1, result_p2);
    }

    static boolean isIncorrect(List<Integer> update, List<List<Integer>> rules) {
        for(List<Integer> rule : rules) {
            int firstRuleIndex = update.indexOf(rule.get(0));
            int secondRuleIndex = update.indexOf(rule.get(1));
            if(firstRuleIndex != -1 && secondRuleIndex != -1 && firstRuleIndex > secondRuleIndex) {
                return true;
            }
        }
        return false;
    }

    static void swapIncorrect(List<Integer> update, List<List<Integer>> rules) {
        for(List<Integer> rule : rules) {
            int firstRuleIndex = update.indexOf(rule.get(0));
            int secondRuleIndex = update.indexOf(rule.get(1));
            if(firstRuleIndex != -1 && secondRuleIndex != -1 && firstRuleIndex > secondRuleIndex) {
                Collections.swap(update, firstRuleIndex, secondRuleIndex);
            }
        }
    }

}