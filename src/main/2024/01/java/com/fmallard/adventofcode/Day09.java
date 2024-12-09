package com.fmallard.adventofcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Day09 {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {

        long startTime = System.nanoTime();
        long result_p1;
        long result_p2;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("09/inputD9.txt");
        List<Integer> fileSystemP1 = readIntegerLine(is);
        List<Integer> fileSystemP2 = new ArrayList<>(fileSystemP1);
        while(hasFreeSpaceInBetween(fileSystemP1)) {
            moveBlockToFreeSpace(fileSystemP1);
        }

        int nextFileIndex = fileSystemP2.size() - 1;
        int firstFreeSpaceIndex = fileSystemP2.indexOf(-1);
        while(firstFreeSpaceIndex < nextFileIndex) {
            nextFileIndex = moveBlockToFreeSpace(fileSystemP2, nextFileIndex);
            firstFreeSpaceIndex = fileSystemP2.indexOf(-1);
        }

        result_p1 = calculateChecksum(fileSystemP1);
        result_p2 = calculateChecksum(fileSystemP2);

        long totalTime = (System.nanoTime() - startTime) / 1000000;
        System.out.printf("Results in %dms : p1=%d, p2=%d%n", totalTime, result_p1, result_p2);
    }

    private static List<Integer> readIntegerLine(InputStream is) throws IOException {
        InputStreamReader streamReader = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(streamReader);

        List<Integer> fileSystem = new ArrayList<>();
        int id = -1;
        for (String line; (line = reader.readLine()) != null;) {
            for (int j = 0; j < line.length(); j++) {
                if(j%2 == 0) {
                    id++;
                }
                int currentInt = Integer.parseInt(String.valueOf(line.charAt(j)));
                for(int i = 0; i < currentInt; i++) {
                    if(j%2 == 0) {
                        fileSystem.add(id);
                    } else {
                        fileSystem.add(-1);
                    }
                }
            }
        }
        return fileSystem;
    }

    private static long calculateChecksum(List<Integer> fileSystem) {
        long result = 0;
        for(int i = 0; i < fileSystem.size(); i++) {
            int currentInt = fileSystem.get(i);
            if(currentInt != -1) {
                result += (long) i * currentInt;
            }
        }
        return result;
    }

    private static void moveBlockToFreeSpace(List<Integer> fileSystem) {
        int firstFreeSpace = fileSystem.indexOf(-1);
        for(int i = fileSystem.size() -1; i > 0; i--) {
            if(fileSystem.get(i) != -1) {
                Integer ch = fileSystem.get(i);
                fileSystem.set(firstFreeSpace, ch);
                fileSystem.set(i, -1);
                break;
            }
        }
    }

    static boolean hasFreeSpaceInBetween(List<Integer> fileSystem) {
        int firstFreeSpace = fileSystem.indexOf(-1);
        for(Integer i : fileSystem.subList(firstFreeSpace, fileSystem.size() - 1)) {
            if(i != -1) {
                return true;
            }
        }
        return false;
    }

    private static int moveBlockToFreeSpace(List<Integer> fileSystem, int nextFileIndex) {
        int nextFileId = fileSystem.get(nextFileIndex);
        int fileSize = computeFileSize(nextFileIndex, fileSystem);
        int sufficientSpaceIndex = canBeMovedToIndex(fileSystem, fileSize);
        if(sufficientSpaceIndex != -1 && sufficientSpaceIndex < nextFileIndex) {
            for(int i = nextFileIndex-fileSize+1; i < nextFileIndex+1; i++) {
                fileSystem.set(i, -1);
            }
            for(int i = sufficientSpaceIndex; i < sufficientSpaceIndex + fileSize; i++) {
                fileSystem.set(i, nextFileId);
            }
        }
        int nextFileEndIndex = sufficientSpaceIndex != -1 && sufficientSpaceIndex < nextFileIndex ? nextFileIndex : nextFileIndex-fileSize;

        while(fileSystem.get(nextFileEndIndex) == -1) {
            nextFileEndIndex--;
        }
        return nextFileEndIndex;
    }

    private static int canBeMovedToIndex(List<Integer> fileSystem, int fileSize) {
        int freeSpaceSize = 0;
        for(int i = 0; i < fileSystem.size(); i++) {
            if(fileSystem.get(i) == -1) {
                freeSpaceSize++;
                if(freeSpaceSize == fileSize) {
                    return i-fileSize+1;
                }
            } else {
                freeSpaceSize = 0;
            }
        }
        return -1;
    }

    static int computeFileSize(int nextFileIndex, List<Integer> fileSystem) {
        int startFileIndex = nextFileIndex;
        int nextFileId = fileSystem.get(nextFileIndex);
        while(fileSystem.get(startFileIndex) == nextFileId) {
            startFileIndex--;
        }
        return nextFileIndex - startFileIndex;
    }

}