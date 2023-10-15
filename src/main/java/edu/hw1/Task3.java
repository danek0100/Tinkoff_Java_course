package edu.hw1;


import java.util.List;

public class Task3 {

    /**
     * Checks if the first array can be nested inside the second array.
     *
     * @param array1 The first array.
     * @param array2 The second array.
     * @return True if array1 can be nested inside array2, false otherwise.
     */
    public static boolean isNestable(List<Integer> array1, List<Integer> array2) {
        if (array1 == null || array2 == null || array1.isEmpty() || array2.isEmpty()) {
            return false;
        }

        int max1 = Integer.MIN_VALUE;
        int min1 = Integer.MAX_VALUE;
        int max2 = Integer.MIN_VALUE;
        int min2 = Integer.MAX_VALUE;

        for (int i : array1) {
            if (i > max1) {
                max1 = i;
            }
            if (i < min1) {
                min1 = i;
            }
        }

        for (int i : array2) {
            if (i > max2) {
                max2 = i;
            }
            if (i < min2) {
                min2 = i;
            }
        }

        return min1 > min2 && max1 < max2;
    }

    private Task3() {}
}
