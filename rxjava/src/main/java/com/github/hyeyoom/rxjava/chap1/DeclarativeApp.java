package com.github.hyeyoom.rxjava.chap1;

import java.util.Arrays;
import java.util.List;

public class DeclarativeApp {
    public static void main(String[] args) {
        // 6보다 큰 홀수들의 합
        final List<Integer> numbers = Arrays.asList(1, 3, 21, 10, 8, 11);

        final int sum = numbers.stream()
                .filter(number -> number > 6 && (number % 2 != 0))
                .mapToInt(n -> n)
                .sum();
        System.out.println("sum = " + sum);
    }
}
