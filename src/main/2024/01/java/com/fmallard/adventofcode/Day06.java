package com.fmallard.adventofcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day06 {

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        long startTime = System.nanoTime();
        int MAP_SIZE = 130;
        Position[] moves = new Position[4];
        moves[0] = new Position(-1,0);
        moves[1] = new Position(0,1);
        moves[2] = new Position(1,0);
        moves[3] = new Position(0,-1);

        int move = 0;
        int result_p1 = 0;
        int result_p2 = 0;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("06/inputD6.txt");
        assert is != null;
        InputStreamReader streamReader = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(streamReader);
        char[][] map = new char[MAP_SIZE][MAP_SIZE];
        int[][] visitedMap = new int[MAP_SIZE][MAP_SIZE];
        int i = 0;
        Position currentPosition = null;
        Position startingPosition = null;
        for (String line; (line = reader.readLine()) != null;) {
            for(int j = 0; j < line.length(); j++){
                char currentChar = line.charAt(j);
                map[i][j] = line.charAt(j);
                visitedMap[i][j] = 0;
                if(currentChar == '^') {
                    startingPosition = new Position(i, j);
                    currentPosition = new Position(i, j);
                    visitedMap[i][j] = 1;
                }
            }
            i++;
        }

        char currentChar;
        assert currentPosition != null;
        createInitialPath(currentPosition, MAP_SIZE, map, moves, move, visitedMap);

        for(int currentLine = 0; currentLine < MAP_SIZE; currentLine++) {
            for(int currentCol = 0; currentCol < MAP_SIZE; currentCol++) {
                if(positionIsInInitialPath(currentCol, visitedMap[currentLine])) {
                    Map<Position, List<Position>> visitedWithDirections = new HashMap<>();
                    currentPosition = new Position(startingPosition.getI(), startingPosition.getJ());
                    currentChar = map[currentLine][currentCol];
                    move = 0;
                    if (currentChar != '^' && currentChar != '#') {
                        map[currentLine][currentCol] = '#';
                        boolean infiniteLoop = false;
                        while (currentPosition.getI() < MAP_SIZE && currentPosition.getI() >= 0
                                && currentPosition.getJ() < MAP_SIZE && currentPosition.getJ() >= 0
                                && !infiniteLoop) {
                            try {
                                if (isNextPositionAvailable(map, currentPosition, moves[move])) {
                                    if(!visitedWithDirections.containsKey(currentPosition)) {
                                        visitedWithDirections.put(currentPosition, new ArrayList<>());
                                    }
                                    if(visitedWithDirections.get(currentPosition).contains(moves[move])) {
                                        infiniteLoop = true;
                                        result_p2++;
                                    }
                                    visitedWithDirections.get(currentPosition).add(moves[move]);
                                    moveToNextPosition(currentPosition, moves[move]);
                                } else {
                                    move = getNextDirection(move);
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                moveToNextPosition(currentPosition, moves[move]);
                            }
                        }

                        map[currentLine][currentCol] = currentChar;
                    }
                }
            }
        }

        for(i = 0; i < MAP_SIZE; i++){
            for(int j = 0; j < MAP_SIZE; j++){
                result_p1 += visitedMap[i][j];
            }
        }
        long totalTime = (System.nanoTime() - startTime) / 1000000;
        System.out.printf("Results in %dms : p1=%d, p2=%d%n", totalTime, result_p1, result_p2);
    }

    private static void createInitialPath(Position currentPosition, int MAP_SIZE, char[][] map, Position[] moves, int directionIndex, int[][] visitedMap) {
        while(currentPosition.getI() < MAP_SIZE && currentPosition.getI()>=0 && currentPosition.getJ() < MAP_SIZE && currentPosition.getJ()>=0) {
            try{
                if(isNextPositionAvailable(map, currentPosition, moves[directionIndex])){
                    moveToNextPosition(currentPosition, moves[directionIndex]);
                    visitedMap[currentPosition.getI()][currentPosition.getJ()] = 1;
                } else {
                    directionIndex = getNextDirection(directionIndex);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                moveToNextPosition(currentPosition, moves[directionIndex]);
            }
        }
    }

    private static int getNextDirection(int move) {
        return (move + 1) % 4;
    }

    private static boolean isNextPositionAvailable(char[][] map, Position currentPosition, Position moves) {
        return map[currentPosition.getI() + moves.getI()][currentPosition.getJ() + moves.getJ()] != '#';
    }

    private static boolean positionIsInInitialPath(int currentCol, int[] visitedMap) {
        return visitedMap[currentCol] == 1;
    }

    private static void moveToNextPosition(Position currentPosition, Position direction) {
        currentPosition.setI(currentPosition.getI() + direction.getI());
        currentPosition.setJ(currentPosition.getJ() + direction.getJ());
    }

}