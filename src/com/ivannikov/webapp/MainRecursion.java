package com.ivannikov.webapp;

public class MainRecursion {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5, 6};

        int sum = recursiveSum(numbers, numbers.length - 1);
        System.out.println("Sum of all array elements: " + sum);

        int countElement = recursiveCount(numbers, numbers.length - 1);
        System.out.println("Number of elements in the array: " + countElement);

        int max = recursiveMax(numbers, numbers.length - 1);
        System.out.println("Maximum element in array: " + max);

        int target = 2;
        int result = recursiveBinarySearch(numbers, 0, numbers.length - 1, target);
        System.out.println(result == -1 ? "Item not found" : target + " element found at position " + result);
    }

    private static int recursiveSum(int[] numbers, int index) {
        if (index <= 0) {
            return 0;
        } else {
            return numbers[index] + recursiveSum(numbers, index - 1);
        }
    }

    private static int recursiveCount(int[] numbers, int index) {
        if (index <= 0) {
            return 0;
        } else {
            return 1 + recursiveCount(numbers, index - 1);
        }
    }

    private static int recursiveMax(int[] numbers, int index) {
        if (index <= 0) {
            return 0;
        } else {
            return Math.max(numbers[index], recursiveMax(numbers, index - 1));
        }
    }

    private static int recursiveBinarySearch(int[] numbers, int left, int right, int target) {
        if (left <= right) {
            int mid = left + (right - left) / 2;
            if (numbers[mid] == target) {
                return mid;
            }
            if (numbers[mid] > target) {
                return recursiveBinarySearch(numbers, left, mid - 1, target);
            }
            return recursiveBinarySearch(numbers, mid + 1, right, target);
        }
        return -1;
    }
}
