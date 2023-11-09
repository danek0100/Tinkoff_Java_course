package edu.hw4;

import java.util.List;
import java.util.stream.Collectors;

public class Task13 {

    public static List<Animal> animalsWithNamesMoreThanTwoWords(List<Animal> animals) {
        return animals.stream()
            .filter(animal -> animal.name().split("\\s+").length > 2)
            .collect(Collectors.toList());
    }

    private Task13() {}
}

