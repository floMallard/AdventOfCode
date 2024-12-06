package com.fmallard.sixth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.*;

public class FirstPart {

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        int MAP_SIZE = 130;
        Position[] moves = new Position[4];
        moves[0] = new Position(-1,0);
        moves[1] = new Position(0,1);
        moves[2] = new Position(1,0);
        moves[3] = new Position(0,-1);


        int move = 0;
        int result = 0;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("06/inputD6.txt");
        InputStreamReader streamReader = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(streamReader);
        char[][] map = new char[MAP_SIZE][MAP_SIZE];
        int[][] visitedMap = new int[MAP_SIZE][MAP_SIZE];
        int i = 0;
        Position currentPosition = null;
        for (String line; (line = reader.readLine()) != null;) {
            for(int j = 0; j < line.length(); j++){
                char currentChar = line.charAt(j);
                map[i][j] = line.charAt(j);
                visitedMap[i][j] = 0;
                if(currentChar == '^') {
                    currentPosition = new Position(i, j);
                    visitedMap[i][j] = 1;
                }
            }
            i++;
        }
        long startTime = System.nanoTime();
        while(currentPosition.getI() < MAP_SIZE && currentPosition.getI()>=0 && currentPosition.getJ() < MAP_SIZE && currentPosition.getJ()>=0) {
            try{
                if(map[currentPosition.getI() + moves[move].getI()][currentPosition.getJ() + moves[move].getJ()] != '#'){
                    currentPosition.setI(currentPosition.getI() + moves[move].getI());
                    currentPosition.setJ(currentPosition.getJ() + moves[move].getJ());
                    visitedMap[currentPosition.getI()][currentPosition.getJ()] = 1;
                } else {
                    move = (move + 1) % 4;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                currentPosition.setI(currentPosition.getI() + moves[move].getI());
                currentPosition.setJ(currentPosition.getJ() + moves[move].getJ());
            }
        }
        long endTime = System.nanoTime();
        for(i = 0; i < MAP_SIZE; i++){
            for(int j = 0; j < MAP_SIZE; j++){
                result += visitedMap[i][j];
            }
        }
        long duration = endTime - startTime;
        System.out.println("Result in " + duration + "ms : " + result);
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
    }



}