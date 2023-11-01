package edu.hw4;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import static edu.hw4.Task19.validateAnimal;

public class Task20 {

    public static String errorsToString(Set<Task19.ValidationError> errors) {
        return errors.stream()
            .map(Task19.ValidationError::getDescription)
            .collect(Collectors.joining(", "));
    }

    public static Map<String, String> findAnimalsWithStringErrors(List<Animal> animals) {
        return animals.stream()
            .collect(Collectors.toMap(
                Animal::name,
                animal -> errorsToString(validateAnimal(animal)),
                (existing, replacement) -> existing, // в случае одинаковых имен, сохраняем первый набор ошибок
                LinkedHashMap::new  // сохраняем порядок вставки
            ))
            .entrySet().stream()
            .filter(entry -> !entry.getValue().isEmpty()) // фильтруем записи без ошибок
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Task20() {}
}

