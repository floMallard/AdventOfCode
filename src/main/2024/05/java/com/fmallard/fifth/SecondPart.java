package com.fmallard.fifth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.*;

public class SecondPart {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        int result = 0;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("05/inputD5.txt");
        InputStreamReader streamReader = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(streamReader);
        List<List<Integer>> rules = new ArrayList<>();
        List<List<Integer>> updates = new ArrayList<>();
        for (String line; (line = reader.readLine()) != null;) {
            if(line.contains("|")) {
                List<Integer> numbers = Arrays.stream(line.split("\\|")).map(Integer::parseInt).toList();
                rules.add(numbers);
            } else if (line.isEmpty()) {
                // skip break
            } else {
                List<Integer> update = Arrays.stream(line.split(",")).map(Integer::parseInt).toList();
                updates.add(update);
            }
        }

        for(List<Integer> update : updates) {
            List<Integer> correct = new ArrayList<>(update.size());
            correct.addAll(update);
            if (!isCorrect(update, rules)) {
                while (!isCorrect(correct, rules)) {
                    correct = swapIncorrect(correct, rules);
                }
                result += correct.get((int) (double) (correct.size() / 2));
            }
        }

        System.out.println(result);
    }

    static boolean isCorrect(List<Integer> update, List<List<Integer>> rules) {
        for(List<Integer> rule : rules) {
            int firstRuleIndex = update.indexOf(rule.get(0));
            int secondRuleIndex = update.indexOf(rule.get(1));
            if(firstRuleIndex != -1 && secondRuleIndex != -1 && firstRuleIndex > secondRuleIndex) {
                return false;
            }
        }
        return true;
    }

    static List<Integer> swapIncorrect(List<Integer> update, List<List<Integer>> rules) {
        for(List<Integer> rule : rules) {
            int firstRuleIndex = update.indexOf(rule.get(0));
            int secondRuleIndex = update.indexOf(rule.get(1));
            if(firstRuleIndex != -1 && secondRuleIndex != -1 && firstRuleIndex > secondRuleIndex) {
                Collections.swap(update, firstRuleIndex, secondRuleIndex);
            }
        }
        return update;
    }
}