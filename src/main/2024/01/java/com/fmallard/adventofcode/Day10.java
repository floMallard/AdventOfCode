package com.fmallard.adventofcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Day10 {
    static int MAP_SIZE = 55;
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        long startTime = System.nanoTime();
        int result_p1;
        int result_p2;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("10/input.txt");
        assert is != null;
        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        List<Position> startPositions = new ArrayList<>();
        int[][] map = new int[MAP_SIZE][MAP_SIZE];
        Map<Position, Set<Position>> trailheadsP1 = new HashMap<>();
        Map<Position, Integer> trailheadsP2 = new HashMap<>();
        int lineIndex = 0;
        for (String line; (line = reader.readLine()) != null;) {
            for (int j = 0; j < line.length(); j++) {
                int currentInt = Integer.parseInt(String.valueOf(line.charAt(j)));
                map[lineIndex][j] = currentInt;
                if(currentInt == 0) {
                    Position startPos = new Position(lineIndex, j);
                    startPositions.add(startPos);
                    trailheadsP1.put(startPos, new HashSet<>());
                    trailheadsP2.put(startPos, 0);
                }
            }
            lineIndex++;
        }

        for(Position p : startPositions) {
            getNextPositionP1(p, p, map, 1, trailheadsP1);
            getNextPositionP2(p,p,map,1, trailheadsP2);
        }
        result_p1 = trailheadsP1.values().stream().mapToInt(Set::size).sum();
        result_p2 = trailheadsP2.values().stream().mapToInt(i -> i).sum();

        long totalTime = (System.nanoTime() - startTime) / 1000000;
        System.out.printf("Results in %dms : p1=%d, p2=%d%n", totalTime, result_p1, result_p2);
    }

    static void getNextPositionP1(Position startPos, Position currentPos, int[][] map, int currentHeight, Map<Position,Set<Position>> pathMap) {
        // move right
        if(currentPos.getJ() + 1 < MAP_SIZE && map[currentPos.getI()][currentPos.getJ() + 1] == currentHeight ) {
            Position newPos = new Position(currentPos.getI(), currentPos.getJ()+1);
            if(currentHeight == 9) {
                pathMap.get(startPos).add(newPos);
            } else {
                getNextPositionP1(startPos, newPos, map,currentHeight+1,pathMap);
            }
        }
        // move left
        if(currentPos.getJ() - 1 >= 0 && map[currentPos.getI()][currentPos.getJ() - 1] == currentHeight ) {
            Position newPos = new Position(currentPos.getI(), currentPos.getJ()-1);
            if(currentHeight == 9) {
                pathMap.get(startPos).add(newPos);
            } else {
                getNextPositionP1(startPos, newPos, map,currentHeight+1,pathMap);
            }
        }
        // move up
        if(currentPos.getI() - 1 >= 0 && map[currentPos.getI()-1][currentPos.getJ()] == currentHeight ) {
            Position newPos = new Position(currentPos.getI()-1, currentPos.getJ());
            if(currentHeight == 9) {
                pathMap.get(startPos).add(newPos);
            } else {
                getNextPositionP1(startPos, newPos, map,currentHeight+1,pathMap);
            }
        }
        // move down
        if(currentPos.getI() + 1 < MAP_SIZE && map[currentPos.getI()+1][currentPos.getJ()] == currentHeight ) {
            Position newPos = new Position(currentPos.getI()+1, currentPos.getJ());
            if(currentHeight == 9) {
                pathMap.get(startPos).add(newPos);
            } else {
                getNextPositionP1(startPos, newPos, map,currentHeight+1,pathMap);
            }
        }
    }

    static void getNextPositionP2(Position startPos, Position currentPos, int[][] map, int currentHeight, Map<Position,Integer> pathMap) {
        // move right
        if(currentPos.getJ() + 1 < MAP_SIZE && map[currentPos.getI()][currentPos.getJ() + 1] == currentHeight ) {
            Position newPos = new Position(currentPos.getI(), currentPos.getJ()+1);
            if(currentHeight == 9) {
                pathMap.put(startPos,pathMap.get(startPos)+1);
            } else {
                getNextPositionP2(startPos, newPos, map,currentHeight+1,pathMap);
            }
        }
        // move left
        if(currentPos.getJ() - 1 >= 0 && map[currentPos.getI()][currentPos.getJ() - 1] == currentHeight ) {
            Position newPos = new Position(currentPos.getI(), currentPos.getJ()-1);
            if(currentHeight == 9) {
                pathMap.put(startPos,pathMap.get(startPos)+1);
            } else {
                getNextPositionP2(startPos, newPos, map,currentHeight+1,pathMap);
            }
        }
        // move up
        if(currentPos.getI() - 1 >= 0 && map[currentPos.getI()-1][currentPos.getJ()] == currentHeight ) {
            Position newPos = new Position(currentPos.getI()-1, currentPos.getJ());
            if(currentHeight == 9) {
                pathMap.put(startPos,pathMap.get(startPos)+1);
            } else {
                getNextPositionP2(startPos, newPos, map,currentHeight+1,pathMap);
            }
        }
        // move down
        if(currentPos.getI() + 1 < MAP_SIZE && map[currentPos.getI()+1][currentPos.getJ()] == currentHeight ) {
            Position newPos = new Position(currentPos.getI()+1, currentPos.getJ());
            if(currentHeight == 9) {
                pathMap.put(startPos,pathMap.get(startPos)+1);
            } else {
                getNextPositionP2(startPos, newPos, map,currentHeight+1,pathMap);
            }
        }
    }
}