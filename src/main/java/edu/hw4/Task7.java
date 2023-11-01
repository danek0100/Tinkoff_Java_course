package edu.hw4;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Task7 {

    public static Optional<Animal> kthOldestAnimal(List<Animal> animals, int k) {
        if (k <= 0 || k > animals.size()) {
            return Optional.empty();
        }

        return animals.stream()
            .sorted(Comparator.comparingInt(Animal::age).reversed())
            .skip(k - 1)
            .findFirst();
    }

    private Task7() {}
}

