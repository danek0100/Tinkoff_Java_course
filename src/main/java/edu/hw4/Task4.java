package edu.hw4;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Task4 {

    public static Optional<Animal> findAnimalWithLongestName(List<Animal> animals) {
        return animals.stream()
            .max(Comparator.comparingInt(a -> a.name().length()));
    }

    private Task4() {}
}

