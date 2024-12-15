package com.fmallard.adventofcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

public class Day15 {
    static int I_SPACE = 50;
    static int J_SPACE = 50;
    static char[][] map = new char[I_SPACE][J_SPACE];
    static char[][] map2 = new char[I_SPACE][2*J_SPACE];
    static Map<Character, Position> movesMap = new HashMap<>();
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {

        long startTime = System.nanoTime();
        long result_p1 = 0;
        long result_p2 = 0;
        movesMap.put('^', new Position(-1,0));
        movesMap.put('v', new Position(1,0));
        movesMap.put('>', new Position(0,1));
        movesMap.put('<', new Position(0,-1));
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("15/input.txt");
        assert is != null;
        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        Position robotPos = null;
        List<Character> moves = new ArrayList<>();
        int index = 0;
        for (String line; (line = reader.readLine()) != null;) {
            if(!line.isEmpty()) {
                if(line.contains("^")) {
                    moves.addAll(line.chars().mapToObj(c -> (char)c).toList());
                } else {
                    for(int i=0;i<I_SPACE;i++){
                        char currentChar = line.charAt(i);
                        map[index][i] = currentChar;
                        if(currentChar == '.' || currentChar == '#') {
                            map2[index][2*i] = currentChar;
                            map2[index][2*i+1] = currentChar;
                        } else if(currentChar == 'O') {
                            map2[index][2*i] = '[';
                            map2[index][2*i+1] = ']';
                        } else if(currentChar == '@') {
                            map2[index][2*i] = currentChar;
                            map2[index][2*i+1] = '.';
                            if(line.charAt(i) == '@') {
                                robotPos = new Position(index, i);
                            }
                        }
                    }
                    index++;
                }
            }
        }
        moveWarehouse(moves, robotPos);
        moveWarehouseP2(moves, robotPos);
        result_p1 = computeGPS();
        long totalTime = (System.nanoTime() - startTime) / 1000000;
        System.out.printf("Results in %dms : p1=%d, p2=%d%n", totalTime, result_p1, result_p2);
    }

    private static void moveWarehouseP2(List<Character> moves, Position robotPos) {

    }

    private static long computeGPS() {
        long result = 0;
        for(int i = 0; i < I_SPACE; i++) {
            for(int j = 0; j < J_SPACE; j++){
                if(map[i][j] == 'O') {
                    result += (i)* 100L + j;
                }
            }
        }
        return result;
    }

    private static void moveWarehouse(List<Character> moves, Position robotPos) {
        for(Character move : moves){
            Position movePos = movesMap.get(move);
            Position robotTargetPos = new Position(robotPos.i + movePos.i, robotPos.j + movePos.j);
            char target = map[robotTargetPos.i][robotTargetPos.j];
            if (target == 'O') {
                boolean hasSpace = false;
                int boxes = 0;
                boolean isWall = false;
                while(!hasSpace && !isWall) {
                    boxes++;
                    isWall = map[robotTargetPos.i+(boxes*movePos.i)][robotTargetPos.j+(boxes*movePos.j)] == '#';
                    hasSpace = map[robotTargetPos.i+(boxes*movePos.i)][robotTargetPos.j+(boxes*movePos.j)] == '.';
                }
                if(hasSpace) {
                    map[robotPos.i][robotPos.j] = '.';
                    map[robotTargetPos.i][robotTargetPos.j] = '@';
                    for(int box=1;box<=boxes;box++) {
                        map[robotTargetPos.i + (box * movePos.i)][robotTargetPos.j + (box * movePos.j)] = 'O';
                    }
                    robotPos = robotTargetPos;
                }
            } else if (target == '.') {
                map[robotPos.i][robotPos.j] = '.';
                map[robotTargetPos.i][robotTargetPos.j] = '@';
                robotPos = robotTargetPos;
            }
        }
    }
}