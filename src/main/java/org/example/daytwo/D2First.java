package org.example.daytwo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class D2First {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("daytwo/inputD2.txt");
        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        int minIncrement = 1;
        int maxIncrement = 3;
        int result = 0;
        for (String line; (line = reader.readLine()) != null;) {
            String[] split = line.split("(\\s+)");
            boolean increasing = Integer.parseInt(split[0]) < Integer.parseInt(split[1]);
            int[] numberInReport = Arrays.stream(split).mapToInt(Integer::parseInt).toArray();
            boolean increase = true;
            for(int i = 0; i < split.length-1; i++) {
                if(increasing) {
                    if(numberInReport[i+1] < numberInReport[i] + minIncrement || numberInReport[i+1] > numberInReport[i] + maxIncrement) {
                        increase = false;
                        break;
                    }
                }
                else if(!increasing) {
                    if(numberInReport[i+1] > numberInReport[i] - minIncrement || numberInReport[i+1] < numberInReport[i] - maxIncrement) {
                        increase = false;
                        break;
                    }
                }
            }
            if(increase) result++;
        }
        System.out.println(result);
    }
}