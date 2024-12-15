package com.fmallard.adventofcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Day12 {

    static int MAP_SIZE = 10;
    static char[][] map = new char[MAP_SIZE][MAP_SIZE];
    static boolean[][] fencedMap = new boolean[MAP_SIZE][MAP_SIZE];
    static List<List<Position>> perimeters = new ArrayList<>();
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {

        long startTime = System.nanoTime();
        long result_p1;
        long result_p2;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("12/test.txt");
        assert is != null;
        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        int lineIndex = 0;
        for (String line; (line = reader.readLine()) != null;) {
            for (int j = 0; j < line.length(); j++) {
                char currentChar = line.charAt(j);
                map[lineIndex][j] = currentChar;
                fencedMap[lineIndex][j] = false;
            }
            lineIndex++;
        }

        result_p1 = fence_p1();

        result_p2 = fence_p2();

        long totalTime = (System.nanoTime() - startTime) / 1000000;
        System.out.printf("Results in %dms : p1=%d, p2=%d%n", totalTime, result_p1, result_p2);
    }

    private static long fence_p1() {
        long result = 0;
        for(int i = 0; i < MAP_SIZE; i++) {
            for(int j = 0; j < MAP_SIZE; j++) {
                if(!fencedMap[i][j]) {
                    Position currentPos = new Position(i,j);
                    char currentChar = map[i][j];
                    List<Position> perimeterForChar = getPerimeter(currentPos, currentChar);
                    if(perimeterForChar.isEmpty()){
                        perimeterForChar.add(currentPos);
                    }
                    perimeters.add(perimeterForChar);
                    int perimeter = perimeterForChar.size();
                    int fences = countDifferentNeighborsForPerimeter(perimeterForChar, currentChar);

                    result += (long) perimeter * fences;
                }
            }
        }
        return result;
    }
    private static long fence_p2() {
        long result = 0;
        for(List<Position> perimeterForChar : perimeters) {
            int perimeter = perimeterForChar.size();
            char currentChar = map[perimeterForChar.get(0).getI()][perimeterForChar.get(0).getJ()];
            int sides = countSides(perimeterForChar, currentChar);

            result += (long) perimeter * sides;
        }

        return result;
    }

    private static int countDifferentNeighborsForPerimeter(List<Position> perimeterForChar, char currentChar) {
        int neighbors = 0;
        for(Position currentPos : perimeterForChar) {
            neighbors += countDifferentNeighbors(currentPos, currentChar);
        }
        return neighbors;
    }

    private static int countDifferentNeighbors(Position currentPos, char currentChar) {
        int neighbors = 0;
        if (currentPos.getJ() + 1 >= MAP_SIZE || map[currentPos.getI()][currentPos.getJ() + 1] != currentChar) {
            neighbors++;
        }
        // move left
        if (currentPos.getJ() - 1 < 0 || map[currentPos.getI()][currentPos.getJ() - 1] != currentChar) {
            neighbors++;
        }
        // move up
        if (currentPos.getI() - 1 < 0 || map[currentPos.getI() - 1][currentPos.getJ()] != currentChar) {
            neighbors++;
        }
        // move down
        if (currentPos.getI() + 1 >= MAP_SIZE || map[currentPos.getI() + 1][currentPos.getJ()] != currentChar) {
            neighbors++;
        }
        return neighbors;
    }

    private static int countDifferentNeighborsP2(Position currentPos, char currentChar) {
        int neighbors = 0;
        if ((currentPos.getJ() + 1 >= MAP_SIZE || map[currentPos.getI()][currentPos.getJ() + 1] != currentChar) &&
                (currentPos.getJ() - 1 < 0 || map[currentPos.getI()][currentPos.getJ() - 1] != currentChar)) {
            neighbors++;
        }
        // move up
        if ((currentPos.getI() - 1 < 0 || map[currentPos.getI() - 1][currentPos.getJ()] != currentChar) &&
                (currentPos.getI() + 1 >= MAP_SIZE || map[currentPos.getI() + 1][currentPos.getJ()] != currentChar)){
            neighbors++;
        }
        return neighbors;
    }

    private static int countSides(List<Position> perimeter, char currentChar) {
        return perimeter.stream().mapToInt(p->countSides(p, currentChar)).sum();
    }

    private static int countSides(Position p, char currentChar) {
        int i = p.getI();
        int j = p.getJ();
        if(i-1 < 0 && j-1 < 0) {
            if(map[i+1][j] != currentChar && map[i][j+1] != currentChar) {
                return 3;
            } else {
                return 2;
            }
        } else if(i+1 == MAP_SIZE && j-1 < 0) {
            if(map[i-1][j] != currentChar && map[i][j+1] != currentChar) {
                return 3;
            } else {
                return 2;
            }
        } else if(i-1 < 0 && j+1 == MAP_SIZE) {
            if(map[i][j-1] != currentChar && map[i+1][j] != currentChar) {
                return 3;
            } else {
                return 2;
            }
        } else if(i+1 == MAP_SIZE && j+1 == MAP_SIZE) {
            if(map[i-1][j] != currentChar && map[i][j-1] != currentChar){
                return 3;
            } else {
                return 2;
            }
        } else {
            return countDifferentNeighborsP2(p, currentChar);
        }
    }

    private static List<Position> getPerimeter(Position currentPos, char currentChar) {
        List<Position> result = new ArrayList<>();
        if(currentPos.getJ() + 1 < MAP_SIZE && map[currentPos.getI()][currentPos.getJ() + 1] == currentChar && !fencedMap[currentPos.getI()][currentPos.getJ()+1]) {
            fencedMap[currentPos.getI()][currentPos.getJ()+1] = true;
            result.add(new Position(currentPos.getI(),currentPos.getJ()+1));
            result.addAll(getPerimeter(new Position(currentPos.getI(), currentPos.getJ()+1), currentChar));
        }
        // move left
        if(currentPos.getJ() - 1 >= 0 && map[currentPos.getI()][currentPos.getJ() - 1] == currentChar && !fencedMap[currentPos.getI()][currentPos.getJ()-1]) {
            fencedMap[currentPos.getI()][currentPos.getJ()-1] = true;
            result.add(new Position(currentPos.getI(),currentPos.getJ()-1));
            result.addAll(getPerimeter(new Position(currentPos.getI(), currentPos.getJ()-1), currentChar));
        }
        // move up
        if(currentPos.getI() - 1 >= 0 && map[currentPos.getI()-1][currentPos.getJ()] == currentChar && !fencedMap[currentPos.getI()-1][currentPos.getJ()]) {
            fencedMap[currentPos.getI()-1][currentPos.getJ()] = true;
            result.add(new Position(currentPos.getI()-1,currentPos.getJ()));
            result.addAll(getPerimeter(new Position(currentPos.getI()-1, currentPos.getJ()), currentChar));
        }
        // move down
        if(currentPos.getI() + 1 < MAP_SIZE && map[currentPos.getI()+1][currentPos.getJ()] == currentChar && !fencedMap[currentPos.getI()+1][currentPos.getJ()]) {
            fencedMap[currentPos.getI()+1][currentPos.getJ()] = true;
            result.add(new Position(currentPos.getI()+1,currentPos.getJ()));
            result.addAll(getPerimeter(new Position(currentPos.getI()+1, currentPos.getJ()), currentChar));
        }
        return result;
    }


}