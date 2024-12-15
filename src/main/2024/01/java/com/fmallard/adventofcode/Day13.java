package com.fmallard.adventofcode;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.LUDecomposition;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13 {

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {

        long startTime = System.nanoTime();
        long result_p1 = 0;
        long result_p2 = 0;

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("13/input.txt");
        assert is != null;
        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        int lineIndex = 0;
        double x1 = 0d,x2 = 0d,y1=0d,y2=0d, x=0d,y=0d;
        Pattern buttonPattern = Pattern.compile("X[+|=](\\d+),.*Y[+|=](\\d+)");
        for (String line; (line = reader.readLine()) != null;) {
            Matcher matcher = buttonPattern.matcher(line);
            if(line.contains("Button A")) {
                while(matcher.find()) {
                    x1 = Integer.parseInt(matcher.group(1));
                    y1 = Integer.parseInt(matcher.group(2));
                }
            } else if(line.contains("Button B")) {
                while(matcher.find()) {
                    x2 = Integer.parseInt(matcher.group(1));
                    y2 = Integer.parseInt(matcher.group(2));
                }
            } else if(line.contains("Prize")) {
                while(matcher.find()) {
                    x = Integer.parseInt(matcher.group(1));
                    y = Integer.parseInt(matcher.group(2));
                }
            } else {
                result_p1 += (long) solve_p1(x1, x2, y1, y2, x, y);
                result_p2 += (long) solve_p1(x1, x2, y1, y2, x+10000000000000d, y+10000000000000d);
            }
        }

        long totalTime = (System.nanoTime() - startTime) / 1000000;
        System.out.printf("Results in %dms : p1=%d, p2=%d%n", totalTime, result_p1, result_p2);
    }

    private static long solve_p1(double x1, double x2, double y1, double y2, double x, double y) {
        double det = x1*y2 - x2*y1;
        double eps = 1e-4d;
        var matButtons = new Array2DRowRealMatrix(new double[][]{
                {x1, x2},
                {y1, y2}
        });
        var matPrize = new ArrayRealVector(new double[]{
                x,y
        });
        var solver = new LUDecomposition(matButtons).getSolver();
        var solution = solver.solve(matPrize);

        var a = solution.getEntry(0);
        var b = solution.getEntry(1);
        if(Math.abs(a - Math.round(a)) < eps && Math.abs(b - Math.round(b)) < eps  // integer solution!
                && Math.round(a) <= Long.MAX_VALUE && Math.round(b) <= Long.MAX_VALUE) {
            return (long) (3d*Math.round(a) + Math.round(b));
        }
        return 0;
    }

}