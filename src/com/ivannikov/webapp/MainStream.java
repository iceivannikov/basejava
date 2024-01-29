package com.ivannikov.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStream {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(oddOrEven(Arrays.asList(1, 2, 3, 3, 2, 3, 3)));
    }

    public static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (left, right) -> left * 10 + right);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        return integers.stream()
                .collect(Collectors.partitioningBy(i -> i % 2 == 0))
                .get(integers
                        .stream()
                        .mapToInt(Integer::intValue)
                        .sum() % 2 == 0);
    }
}