package edu.hw4;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Task5 {

    public static Animal.Sex dominantSex(List<Animal> animals) {
        Map<Animal.Sex, Long> sexCounts = animals.stream()
            .collect(Collectors.groupingBy(Animal::sex, Collectors.counting()));

        long maleCount = sexCounts.getOrDefault(Animal.Sex.M, 0L);
        long femaleCount = sexCounts.getOrDefault(Animal.Sex.F, 0L);

        return maleCount > femaleCount ? Animal.Sex.M : Animal.Sex.F;
    }

    private Task5() {}
}

