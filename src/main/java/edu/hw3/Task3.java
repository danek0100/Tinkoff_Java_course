package edu.hw3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides utility methods for computing the frequency of items in a list.
 */
public class Task3 {


    /**
     * Returns a frequency dictionary of the given list.
     *
     * @param list The list of items to compute the frequency for.
     * @param <T>  The type of items in the list.
     * @return A map where keys are unique items from the list and values are their frequencies.
     */
    public static <T> Map<T, Integer> freqDict(List<T> list) {
        Map<T, Integer> frequencyMap = new HashMap<>();

        for (T item : list) {
            frequencyMap.put(item, frequencyMap.getOrDefault(item, 0) + 1);
        }

        return frequencyMap;
    }

    private Task3() {}
}
