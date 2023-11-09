package edu.hw4;

import java.util.List;

public class Task9 {

    public static int totalPawsCount(List<Animal> animals) {
        return animals.stream()
            .mapToInt(Animal::paws)
            .sum();
    }

    private Task9() {}
}

