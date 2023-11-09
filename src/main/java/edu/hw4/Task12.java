package edu.hw4;

import java.util.List;

public class Task12 {

    public static long animalsWeighingMoreThanHeight(List<Animal> animals) {
        return animals.stream()
            .filter(animal -> animal.weight() > animal.height())
            .count();
    }

    private Task12() {}
}

