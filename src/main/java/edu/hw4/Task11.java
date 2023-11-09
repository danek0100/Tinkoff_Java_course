package edu.hw4;

import java.util.List;
import java.util.stream.Collectors;

public class Task11 {

    private static final int CONST_ANIMAL_HEIGHT = 100;

    public static List<Animal> bitingAnimalsOver100cm(List<Animal> animals) {
        return animals.stream()
            .filter(animal -> (animal.bites() == null || animal.bites()) && animal.height() > CONST_ANIMAL_HEIGHT)
            .collect(Collectors.toList());
    }

    private Task11() {}
}

