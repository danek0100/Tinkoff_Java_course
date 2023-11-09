package edu.hw4;

import java.util.List;

public class Task17 {

    public static boolean doSpidersBiteMoreThanDogs(List<Animal> animals) {
        long bitingSpiders = animals.stream()
            .filter(animal -> animal.type() == Animal.Type.SPIDER && (animal.bites() == null || animal.bites()))
            .count();

        long bitingDogs = animals.stream()
            .filter(animal -> animal.type() == Animal.Type.DOG && (animal.bites() == null || animal.bites()))
            .count();

        return bitingSpiders > bitingDogs;
    }

    private Task17() {}
}

