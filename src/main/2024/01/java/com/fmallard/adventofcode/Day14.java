package com.fmallard.adventofcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Day14 {
    static int I_SPACE = 103;
    static int J_SPACE = 101;
    static int[][] map = new int[I_SPACE][J_SPACE];
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {

        long startTime = System.nanoTime();
        long result_p1 = 0;
        long result_p2 = 0;

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("14/input.txt");
        assert is != null;
        int firstQuadrant = 0, secondQuadrant = 0, thirdQuadrant = 0, fourthQuadrant = 0;
        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        List<Position> positions = new ArrayList<>();
        List<Position> velocities = new ArrayList<>();
        Pattern positionPattern = Pattern.compile("p=(?<j>\\d+),(?<i>\\d+) v=(?<vj>-?\\d+),(?<vi>-?\\d+)");
        for (String line; (line = reader.readLine()) != null;) {
            var pos = positionPattern.matcher(line);
            pos.matches();
            Position init = new Position(Integer.parseInt(pos.group("i")), Integer.parseInt(pos.group("j")));
            Position vel = new Position(Integer.parseInt(pos.group("vi")), Integer.parseInt(pos.group("vj")));
            positions.add(init);
            velocities.add(vel);
        }
        boolean couldBeTree = false;
        long second = 0;
        while(!couldBeTree) {
            int index = 0;
            for (Position p : positions) {
                int newi = (p.getI() + velocities.get(index).getI()) % I_SPACE;
                int newj = (p.getJ() + velocities.get(index).getJ()) % J_SPACE;
                if (newi < 0) {
                    newi += I_SPACE;
                }
                if (newj < 0) {
                    newj += J_SPACE;
                }
                p.setI(newi);
                p.setJ(newj);
                map[newi][newj]++;
                if (newi < Math.floor((float) I_SPACE / 2) && newj < Math.floor((float) J_SPACE / 2)) {
                    firstQuadrant++;
                } else if (newi < Math.floor((float) I_SPACE / 2) && newj > Math.floor((float) J_SPACE / 2)) {
                    secondQuadrant++;
                } else if (newi > Math.floor((float) I_SPACE / 2) && newj < Math.floor((float) J_SPACE / 2)) {
                    thirdQuadrant++;
                } else if (newi > Math.floor((float) I_SPACE / 2) && newj > Math.floor((float) J_SPACE / 2)) {
                    fourthQuadrant++;
                }
                index++;
            }
            second++;
            couldBeTree = map[0][(int) (double) (J_SPACE / 2)] == 1
                    && map[1][49]==1
            &&  map[1][50] == 1
            && map[1][51] == 1;
            if(!couldBeTree) {
                for (int i = 0; i < I_SPACE; i++) {
                    Arrays.fill(map[i], 0);
                }
            }
        }
        result_p1 = (long) firstQuadrant * secondQuadrant * thirdQuadrant * fourthQuadrant;
        result_p2 = second;
        long totalTime = (System.nanoTime() - startTime) / 1000000;
        System.out.printf("Results in %dms : p1=%d, p2=%d%n", totalTime, result_p1, result_p2);
    }

    private static long solve_p1(double x1, double x2, double y1, double y2, double x, double y) {

        return 0;
    }

}