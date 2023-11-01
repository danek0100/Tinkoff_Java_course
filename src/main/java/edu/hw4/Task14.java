package edu.hw4;

import java.util.List;

public class Task14 {

    public static boolean hasDogTallerThan(List<Animal> animals, int k) {
        return animals.stream()
            .filter(animal -> animal.type() == Animal.Type.DOG)
            .anyMatch(dog -> dog.height() > k);
    }

    private Task14() {}
}

