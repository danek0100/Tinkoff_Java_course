package edu.hw4;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Task18 {

    @SafeVarargs
    public static Optional<Animal> heaviestFishFromLists(List<Animal>... lists) {
        return Stream.of(lists)
            .flatMap(List::stream)
            .filter(animal -> animal.type() == Animal.Type.FISH)
            .max(Comparator.comparingInt(Animal::weight));
    }

    private Task18() {}
}

