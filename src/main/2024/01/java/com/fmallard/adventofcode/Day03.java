package com.fmallard.adventofcode;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        long startTime = System.nanoTime();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("03/inputD3.txt");
        assert is != null;
        String input = new Scanner(is, StandardCharsets.UTF_8).useDelimiter("\\A").next();
        Pattern mulPattern = Pattern.compile("(mul\\(\\d{1,3},\\d{1,3}\\))");
        Matcher matcher = mulPattern.matcher(input);
        List<String> matches_p1 = new ArrayList<>();
        List<String> validStrings = new ArrayList<>();
        int result_p1 = 0;
        while(matcher.find()) {
            matches_p1.add(matcher.group(1));
        }

        int index = 0;
        boolean endsWithDo = false;
        while(input.contains("don't()")) {
            String substring = input.substring(index, input.indexOf("don't()"));
            validStrings.add(substring);
            input = input.substring(input.indexOf("don't()")+7);
            endsWithDo = false;
            if(input.contains("do()")) {
                input = input.substring(input.indexOf("do()"));
                endsWithDo = true;
            }
        }
        if(endsWithDo) {
            validStrings.add(input);
        }

        List<String> matches = new ArrayList<>();
        int result_p2 = 0;
        for(String validString : validStrings) {
            matcher = mulPattern.matcher(validString);
            while (matcher.find()) {
                matches.add(matcher.group(1));
            }
        }

        Pattern digitsPattern = Pattern.compile("(\\d{1,3})");
        for(String match : matches_p1) {
            Matcher digitMatcher = digitsPattern.matcher(match);
            int add = 1;
            while (digitMatcher.find()) {
                add *= Integer.parseInt(digitMatcher.group(1));
            }
            result_p1 += add;
        }
        for(String match : matches) {
            Matcher digitMatcher = digitsPattern.matcher(match);
            int add = 1;
            while (digitMatcher.find()) {
                add *= Integer.parseInt(digitMatcher.group(1));
            }
            result_p2 += add;
        }
        long totalTime = (System.nanoTime() - startTime) / 1000000;
        System.out.printf("Results in %dms : p1=%d, p2=%d%n", totalTime, result_p1, result_p2);
    }
}