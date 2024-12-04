package org.example.dayone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class D1First {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("dayone/InputD1.txt");
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

        int result = 0;
        for(int i = 0; i < col1.size(); i++) {
            result += Math.abs(col1.get(i) - col2.get(i));
        }
        System.out.println(result);
    }
}