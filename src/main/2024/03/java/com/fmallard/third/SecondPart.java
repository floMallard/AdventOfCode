package com.fmallard.third;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecondPart {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("03/inputD3.txt");
        assert is != null;
        String input = new Scanner(is, StandardCharsets.UTF_8).useDelimiter("\\A").next();

        List<String> validStrings = new ArrayList<>();
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

        Pattern mulPattern = Pattern.compile("(mul\\(\\d{1,3},\\d{1,3}\\))");
        List<String> matches = new ArrayList<>();
        int result = 0;
        for(String validString : validStrings) {
            Matcher matcher = mulPattern.matcher(validString);
            while (matcher.find()) {
                matches.add(matcher.group(1));
            }
        }
        Pattern digitsPattern = Pattern.compile("(\\d{1,3})");
        for(String match : matches) {
            Matcher digitMatcher = digitsPattern.matcher(match);
            int add = 1;
            while (digitMatcher.find()) {
                add *= Integer.parseInt(digitMatcher.group(1));
            }
            result += add;
        }
        System.out.println(result);
    }
}