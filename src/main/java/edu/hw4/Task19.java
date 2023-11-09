package edu.hw4;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Task19 {

    public enum ValidationError {
        NEGATIVE_AGE("Отрицательный возраст"),
        NEGATIVE_HEIGHT("Отрицательный рост"),
        NEGATIVE_WEIGHT("Отрицательный вес"),
        INVALID_NAME("Недопустимое имя");


        private final String description;

        ValidationError(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public static Set<ValidationError> validateAnimal(Animal animal) {
        Set<ValidationError> errors = new HashSet<>();

        if (animal.age() < 0) {
            errors.add(ValidationError.NEGATIVE_AGE);
        }

        if (animal.height() < 0) {
            errors.add(ValidationError.NEGATIVE_HEIGHT);
        }

        if (animal.weight() < 0) {
            errors.add(ValidationError.NEGATIVE_WEIGHT);
        }

        if (animal.name() == null || animal.name().trim().isEmpty()) {
            errors.add(ValidationError.INVALID_NAME);
        }

        return errors;
    }

    public static Map<String, Set<ValidationError>> findAnimalsWithErrors(List<Animal> animals) {
        return animals.stream()
            .collect(Collectors.toMap(
                Animal::name,
                Task19::validateAnimal,
                (existing, replacement) -> existing, // в случае одинаковых имен, сохраняем первый набор ошибок
                LinkedHashMap::new  // сохраняем порядок вставки
            ))
            .entrySet().stream()
            .filter(entry -> !entry.getValue().isEmpty()) // фильтруем записи без ошибок
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Task19() {}
}

