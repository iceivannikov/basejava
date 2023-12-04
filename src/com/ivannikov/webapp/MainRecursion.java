package com.ivannikov.webapp;

import java.util.Arrays;

public class MainRecursion {
    public static void main(String[] args) {
        int[] numbers = {5, 3, 8, 6, 2, 7, 1, 4};
        System.out.println("Source array: " + Arrays.toString(numbers));

        int sum = recursiveSum(numbers, numbers.length - 1);
        System.out.printf("Sum of all array elements: %d%n", sum);

        int countElement = recursiveCount(numbers, numbers.length - 1);
        System.out.printf("Number of elements in the array: %d%n", countElement);

        int max = recursiveMax(numbers, numbers.length - 1);
        System.out.printf("Maximum element in array: %d%n", max);

        int[] sort = quickSort(numbers, 0, numbers.length - 1);
        System.out.println("Sort array: " + Arrays.toString(sort));

        int target = 5;
        int result = recursiveBinarySearch(numbers, 0, numbers.length - 1, target);
        System.out.print("Binary search: ");
        System.out.println(result == -1 ? "item not found" : target + " element found at position " + result);

        int number = 5;
        int factorial = factorial(number);
        System.out.printf("Factorial number %d is %d%n", number, factorial);
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

    private static int[] quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(array, low, high);
            quickSort(array, low, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, high);
        }
        return array;
    }

    private static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;

        return i + 1;
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

    private static int factorial(int number) {
        if (number == 1) {
            return 1;
        } else {
            return number * factorial(number - 1);
        }
    }
}
