package com.github.hyeyoom.rxjava.chap1;

import java.util.Arrays;
import java.util.List;

public class ImperativeApp {
    public static void main(String[] args) {
        // 6보다 큰 홀수들의 합
        final List<Integer> numbers = Arrays.asList(1, 3, 21, 10, 8, 11);

        int sum = 0;
        for (int number : numbers) {
            if (number > 6 && (number % 2 != 0)) {
                sum += number;
            }
        }
        System.out.println("sum = " + sum);
    }
}
