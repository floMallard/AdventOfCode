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

public class Day08 {
    static int MAP_SIZE = 50;
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        long startTime = System.nanoTime();
        long result_p1 = 0;
        long result_p2 = 0;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("08/inputD8.txt");
        InputStreamReader streamReader = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(streamReader);

        char[][] map = new char[MAP_SIZE][MAP_SIZE];
        int[][] visitedMap_p1 = new int[MAP_SIZE][MAP_SIZE];
        int[][] visitedMap_p2 = new int[MAP_SIZE][MAP_SIZE];
        Map<Character,List<Position>> charPositions = new HashMap<>();
        int i = 0;
        for (String line; (line = reader.readLine()) != null;) {
            for (int j = 0; j < line.length(); j++) {
                char currentChar = line.charAt(j);
                map[i][j] = currentChar;
                visitedMap_p1[i][j] = 0;
                visitedMap_p2[i][j] = 0;
                if (currentChar != '.') {
                    if (!charPositions.containsKey(currentChar)) {
                        charPositions.put(currentChar, new ArrayList<>());
                    }
                    visitedMap_p2[i][j] = 1;
                    charPositions.get(currentChar).add(new Position(i, j));
                }
            }
            i++;
        }

        computeAntiNodesP1(charPositions, visitedMap_p1);
        computeAntiNodesP2(charPositions, visitedMap_p2);

        for(i = 0; i < MAP_SIZE; i++){
            for(int j = 0; j < MAP_SIZE; j++) {
                result_p1 += visitedMap_p1[i][j];
            }
        }
        for(i = 0; i < MAP_SIZE; i++){
            for(int j = 0; j < MAP_SIZE; j++) {
                result_p2 += visitedMap_p2[i][j];
            }
        }
        long totalTime = (System.nanoTime() - startTime) / 100000;
        System.out.printf("Results in %dms : p1=%d, p2=%d%n", totalTime, result_p1, result_p2);
    }

    private static void computeAntiNodesP1(Map<Character, List<Position>> charPositions, int[][] visitedMap_p1) {
        for(Character ch : charPositions.keySet()) {
            for(Position pos : charPositions.get(ch)) {
                for(Position pos2 : charPositions.get(ch)) {
                    if(!pos.equals(pos2)) {
                        int diffI = Math.abs(pos.getI() - pos2.getI());
                        int diffJ = Math.abs(pos.getJ() - pos2.getJ());
                        int firstAntiNodeI,firstAntiNodeJ,secondAntiNodeI,secondAntiNodeJ;
                        if(pos.getI() >= pos2.getI() && pos.getJ() >= pos2.getJ()) {
                            firstAntiNodeI = pos.getI() + diffI;
                            firstAntiNodeJ = pos.getJ() + diffJ;
                            secondAntiNodeI = pos2.getI() - diffI;
                            secondAntiNodeJ = pos2.getJ() - diffJ;
                        } else if(pos.getI() <= pos2.getI() && pos.getJ() <= pos2.getJ()){
                            firstAntiNodeI = pos.getI() - diffI;
                            firstAntiNodeJ = pos.getJ() - diffJ;
                            secondAntiNodeI = pos2.getI() + diffI;
                            secondAntiNodeJ = pos2.getJ() + diffJ;
                        } else if(pos.getI() <= pos2.getI() && pos.getJ() >= pos2.getJ()){
                            firstAntiNodeI = pos.getI() - diffI;
                            firstAntiNodeJ = pos.getJ() + diffJ;
                            secondAntiNodeI = pos2.getI() + diffI;
                            secondAntiNodeJ = pos2.getJ() - diffJ;
                        } else {
                            firstAntiNodeI = pos.getI() + diffI;
                            firstAntiNodeJ = pos.getJ() - diffJ;
                            secondAntiNodeI = pos2.getI() - diffI;
                            secondAntiNodeJ = pos2.getJ() + diffJ;
                        }

                        if(isInBounds(firstAntiNodeI, firstAntiNodeJ)) {
                            visitedMap_p1[firstAntiNodeI][firstAntiNodeJ] = 1;
                        }
                        if(isInBounds(secondAntiNodeI, secondAntiNodeJ)) {
                            visitedMap_p1[secondAntiNodeI][secondAntiNodeJ] = 1;
                        }
                    }
                }
            }
        }
    }

    private static void computeAntiNodesP2(Map<Character, List<Position>> charPositions, int[][] visitedMap) {
        for(Character ch : charPositions.keySet()) {
            for(Position pos : charPositions.get(ch)) {
                for(Position pos2 : charPositions.get(ch)) {
                    if(!pos.equals(pos2)) {
                        int diffI = Math.abs(pos.getI() - pos2.getI());
                        int diffJ = Math.abs(pos.getJ() - pos2.getJ());
                        int firstAntiNodeI,firstAntiNodeJ,secondAntiNodeI,secondAntiNodeJ;
                        if(pos.getI() >= pos2.getI() && pos.getJ() >= pos2.getJ()) {
                            int n = 1;
                            do {
                                firstAntiNodeI = pos.getI() + n*diffI;
                                firstAntiNodeJ = pos.getJ() + n*diffJ;
                                n++;
                                if(isInBounds(firstAntiNodeI, firstAntiNodeJ))
                                    visitedMap[firstAntiNodeI][firstAntiNodeJ] = 1;
                            } while(isInBounds(firstAntiNodeI,firstAntiNodeJ));
                            n = 1;
                            do {
                                secondAntiNodeI = pos2.getI() - n*diffI;
                                secondAntiNodeJ = pos2.getJ() - n*diffJ;
                                n++;
                                if(isInBounds(secondAntiNodeI, secondAntiNodeJ)) {
                                    visitedMap[secondAntiNodeI][secondAntiNodeJ] = 1;
                                }
                            } while (isInBounds(secondAntiNodeI,secondAntiNodeJ));
                        } else if(pos.getI() <= pos2.getI() && pos.getJ() <= pos2.getJ()){
                            int n = 1;
                            do {
                                firstAntiNodeI = pos.getI() - n * diffI;
                                firstAntiNodeJ = pos.getJ() - n * diffJ;
                                n++;
                                if(isInBounds(firstAntiNodeI, firstAntiNodeJ))
                                    visitedMap[firstAntiNodeI][firstAntiNodeJ] = 1;
                            } while (isInBounds(firstAntiNodeI,firstAntiNodeJ));
                            n = 1;
                            do {
                                secondAntiNodeI = pos2.getI() + n * diffI;
                                secondAntiNodeJ = pos2.getJ() + n * diffJ;
                                n++;
                                if(isInBounds(secondAntiNodeI, secondAntiNodeJ)) {
                                    visitedMap[secondAntiNodeI][secondAntiNodeJ] = 1;
                                }
                            } while(isInBounds(secondAntiNodeI,secondAntiNodeJ));
                        } else if(pos.getI() <= pos2.getI() && pos.getJ() >= pos2.getJ()){
                            int n = 1;
                            do {
                                firstAntiNodeI = pos.getI() - n * diffI;
                                firstAntiNodeJ = pos.getJ() + n * diffJ;
                                n++;
                                if(isInBounds(firstAntiNodeI, firstAntiNodeJ)) {
                                    visitedMap[firstAntiNodeI][firstAntiNodeJ] = 1;
                                }
                            } while (isInBounds(firstAntiNodeI, firstAntiNodeJ));
                            n = 1;
                            do {
                                secondAntiNodeI = pos2.getI() + n * diffI;
                                secondAntiNodeJ = pos2.getJ() - n * diffJ;
                                n++;
                                if(isInBounds(secondAntiNodeI, secondAntiNodeJ)) {
                                    visitedMap[secondAntiNodeI][secondAntiNodeJ] = 1;
                                }
                            } while (isInBounds(secondAntiNodeI, secondAntiNodeJ));
                        } else {
                            int n = 1;
                            do {
                                firstAntiNodeI = pos.getI() + n * diffI;
                                firstAntiNodeJ = pos.getJ() - n * diffJ;
                                n++;
                                if(isInBounds(firstAntiNodeI, firstAntiNodeJ)) {
                                    visitedMap[firstAntiNodeI][firstAntiNodeJ] = 1;
                                }
                            } while (isInBounds(firstAntiNodeI, firstAntiNodeJ));
                            n = 1;
                            do{
                                secondAntiNodeI = pos2.getI() - n*diffI;
                                secondAntiNodeJ = pos2.getJ() + n*diffJ;
                                n++;
                                if(isInBounds(secondAntiNodeI, secondAntiNodeJ)) {
                                    visitedMap[secondAntiNodeI][secondAntiNodeJ] = 1;
                                }
                            } while(isInBounds(secondAntiNodeI, secondAntiNodeJ));
                        }

                    }
                }
            }
        }
    }

    private static boolean isInBounds(int firstAntiNodeI, int firstAntiNodeJ) {
        return firstAntiNodeJ < MAP_SIZE && firstAntiNodeJ >= 0 && firstAntiNodeI < MAP_SIZE && firstAntiNodeI >= 0;
    }

    static class Position {
        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

        int i;

        public int getJ() {
            return j;
        }

        public void setJ(int j) {
            this.j = j;
        }

        int j;

        public Position(int i, int j) {
            this.i = i;
            this.j = j;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return i == position.i && j == position.j;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "i=" + i +
                    ", j=" + j +
                    '}';
        }

        @Override
        public int hashCode() {
            return this.toString().hashCode();
        }
    }
}