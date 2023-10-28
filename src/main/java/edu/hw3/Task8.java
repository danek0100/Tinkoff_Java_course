package edu.hw3;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Contains a custom iterator implementation for traversing collections in reverse.
 */
public class Task8 {

    /**
     * An iterator that traverses a given collection in reverse order.
     *
     * @param <T> the type of elements returned by this iterator.
     */
    public static class BackwardIterator<T> implements Iterator<T> {

        private final Deque<T> deque;

        /**
         * Constructs a new backward iterator for the given collection.
         *
         * @param collection the collection to be traversed in reverse.
         */
        public BackwardIterator(Collection<T> collection) {
            this.deque = new ArrayDeque<>(collection);
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         *
         * @return {@code true} if the iteration has more elements, otherwise {@code false}.
         */
        @Override
        public boolean hasNext() {
            return !deque.isEmpty();
        }

        /**
         * Returns the next element in the iteration in reverse order.
         *
         * @return the next element in the iteration.
         * @throws NoSuchElementException if the iteration has no more elements.
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return deque.pollLast();
        }
    }
}
