package edu.hw4;

import java.util.List;
import java.util.stream.Collectors;

public class Task10 {

    public static List<Animal> animalsWithMismatchedAgeAndPaws(List<Animal> animals) {
        return animals.stream()
            .filter(animal -> animal.age() != animal.paws())
            .collect(Collectors.toList());
    }

    private Task10() {}
}

