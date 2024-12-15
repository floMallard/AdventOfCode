package com.fmallard.adventofcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class Day11 {

    static Map<Key, Long> cache = new ConcurrentHashMap<>();

    record Key(Long stoneno, int cnt) {}

    record Stone(long number) {

        Stream<Stone> getNextStone() {
            if (number == 0) return Stream.of(new Stone(1));
            var s = "" + number;
            if (s.length() % 2 == 0)
                return Stream.of(
                        new Stone(Long.valueOf(s.substring(0, s.length() / 2))),
                        new Stone(Long.valueOf(s.substring(s.length() / 2, s.length()))));
            return Stream.of(new Stone(2024 * number));
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(calc(25, readFile()));
        System.out.println(calc(75, readFile()));
    }

    static Long calc(int cnt, final Stream<Stone> stoneStream) {
        if (cnt == 0) return stoneStream.count();
        return stoneStream.mapToLong(stone -> calc(cnt, stone)).sum();
    }

    static Long calc(int cnt, Stone stone) {
        var key = new Key(stone.number, cnt);
        var value = cache.get(key);
        if (value == null) {
            value = calc(cnt - 1, stone.getNextStone());
            cache.put(key, value);
        }
        return value;
    }

    static Stream<Stone> readFile() throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("11/input.txt");
        assert is != null;
        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        List<Long> stones = new ArrayList<>();

        for (String line; (line = reader.readLine()) != null;) {
            for(String number : line.split(" ")){
                stones.add(Long.parseLong(number));
            }
        }
        return stones.stream()
                .mapToLong(Long::valueOf)
                .mapToObj(Stone::new);
    }
}