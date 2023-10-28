package edu.hw3;

import java.util.Comparator;

/**
 * This class provides a utility to work with a {@code TreeMap} which supports {@code null} keys.
 */
public class Task7 {

    /**
     * A comparator that supports comparison with {@code null} values.
     * @param <T> The type of objects that may be compared by this comparator. The type should be {@code Comparable}.
     */
    public static class NullFriendlyComparator<T extends Comparable<T>> implements Comparator<T> {

        /**
         * Compares its two arguments for order, considering {@code null} values.
         *
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         * @return a -1, 0, or 1 as the first argument is less than, equal to, or greater than the second.
         */
        @Override
        public int compare(T o1, T o2) {
            if (o1 == o2) {
                return 0; // Both are null or both are same objects
            }

            if (o1 == null) {
                return -1; // null is considered less than non-null
            }

            if (o2 == null) {
                return 1; // non-null is considered greater than null
            }

            return o1.compareTo(o2);
        }
    }
}
