package org.example.daythree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class D3First {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("daythree/inputD3.txt");
        String input = new Scanner(is, StandardCharsets.UTF_8).useDelimiter("\\A").next();
        Pattern mulPattern = Pattern.compile("(mul\\(\\d{1,3},\\d{1,3}\\))");
        Matcher matcher = mulPattern.matcher(input);
        List<String> matches = new ArrayList();
        int result = 0;
        while(matcher.find()) {
            matches.add(matcher.group(1));
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