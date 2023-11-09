package edu.hw4;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Task6 {

    public static Map<Animal.Type, Animal> heaviestAnimalByType(List<Animal> animals) {
        return animals.stream()
            .collect(Collectors.toMap(
                Animal::type,
                Function.identity(),
                BinaryOperator.maxBy(
                    Comparator.comparingInt(
                        Animal::weight
                    )
                )
            ));
    }

    private Task6() {}
}

