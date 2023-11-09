package edu.hw4;

import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalTest {

    private final List<Animal> testAnimals = List.of(
            new Animal("Макс", Animal.Type.DOG, Animal.Sex.M, 5, 50, 20, true),
            new Animal("Мистер Пушистик", Animal.Type.CAT, Animal.Sex.M, 3, 30, 10, false),
            new Animal("Твитти", Animal.Type.BIRD, Animal.Sex.F, 1, 15, 5, false),
            new Animal("Дори", Animal.Type.FISH, Animal.Sex.F, 2, 5, 1, false),
            new Animal("Чарли", Animal.Type.SPIDER, Animal.Sex.M, 1, 2, 1, true),
            new Animal("Белла", Animal.Type.CAT, Animal.Sex.F, 4, 32, 11, true),
            new Animal("Люси", Animal.Type.DOG, Animal.Sex.F, 6, 60, 25, null),
            new Animal("Пенни", Animal.Type.BIRD, Animal.Sex.F, 2, 18, 6, true),
            new Animal("Оскар", Animal.Type.FISH, Animal.Sex.M, 1, 6, 2, false),
            new Animal("Брюс", Animal.Type.SPIDER, Animal.Sex.M, 3, 3, 1, false),
            new Animal("Джек", Animal.Type.DOG, Animal.Sex.M, 7, 120, 23, true),
            new Animal("Луна", Animal.Type.CAT, Animal.Sex.F, 3, 29, 10, false),
            new Animal("Джесси", Animal.Type.BIRD, Animal.Sex.M, 1, 17, 5, false),
            new Animal("Баблс", Animal.Type.FISH, Animal.Sex.F, 1, 4, 1, true),
            new Animal("Сид", Animal.Type.SPIDER, Animal.Sex.M, 2, 2, 1, true),
            new Animal("Бенджи", Animal.Type.DOG, Animal.Sex.M, 8, 58, 24, false),
            new Animal("Молли", Animal.Type.CAT, Animal.Sex.F, 5, 31, 12, true),
            new Animal("Твикс", Animal.Type.BIRD, Animal.Sex.M, 2, 16, 6, null),
            new Animal("Финн", Animal.Type.FISH, Animal.Sex.M, 2, 5, 2, false),
            new Animal("Тарзан", Animal.Type.SPIDER, Animal.Sex.M, 1, 3, 1, false)
        );


    @Test
    public void testSortByHeightAscending() {
        List<Animal> sortedAnimals = Task1.sortByHeightAscending(testAnimals);

        // Проверяем, что список отсортирован в порядке возрастания
        assertThat(sortedAnimals)
            .isSortedAccordingTo(Comparator.comparingInt(Animal::height));
    }

    @Test
    public void testGetKHeaviestAnimalsCorrectSize() {
        int k = 5;
        List<Animal> heaviestAnimals = Task2.getKHeaviestAnimals(testAnimals, k);

        assertThat(heaviestAnimals).hasSize(k);
    }

    @Test
    public void testGetKHeaviestAnimalsCorrectAnimals() {
        int k = 5;
        List<Animal> heaviestAnimals = Task2.getKHeaviestAnimals(testAnimals, k);

        // Проверяем, что список отсортирован в порядке убывания веса
        assertThat(heaviestAnimals)
            .isSortedAccordingTo(Comparator.comparingInt(Animal::weight).reversed());
    }

    @Test
    public void testGetKHeaviestAnimalsWithLargeK() {
        int k = 50;  // значение больше, чем количество животных в списке
        List<Animal> heaviestAnimals = Task2.getKHeaviestAnimals(testAnimals, k);

        assertThat(heaviestAnimals)
            .hasSize(testAnimals.size())
            .isSortedAccordingTo(Comparator.comparingInt(Animal::weight).reversed());
    }

    @Test
    public void testCountAnimalsByType() {
        Map<Animal.Type, Long> animalCounts = Task3.countAnimalsByType(testAnimals);

        // Проверяем, что количество для каждого типа соответствует ожидаемому
        for (Animal.Type type : Animal.Type.values()) {
            long expectedCount = testAnimals.stream().filter(animal -> animal.type() == type).count();
            assertThat(animalCounts).containsEntry(type, expectedCount);
        }
    }

    @Test
    public void testCountAnimalsByTypeNoExtraTypes() {
        Map<Animal.Type, Long> animalCounts = Task3.countAnimalsByType(testAnimals);

        // Проверяем, что в карте нет записей для типов, которых нет в списке
        assertThat(animalCounts.keySet()).containsExactlyInAnyOrderElementsOf(
            testAnimals.stream().map(Animal::type).collect(Collectors.toSet())
        );
    }

    @Test
    public void testFindAnimalWithLongestName() {
        Optional<Animal> animalWithLongestName = Task4.findAnimalWithLongestName(testAnimals);

        // Проверяем, что возвращаемое животное имеет самое длинное имя из списка
        int maxLength = testAnimals.stream()
            .mapToInt(a -> a.name().length())
            .max()
            .orElse(0);

        assertThat(animalWithLongestName)
            .isPresent()
            .hasValueSatisfying(animal -> assertThat(animal.name().length()).isEqualTo(maxLength));
    }


    @Test
    public void testFindAnimalWithLongestNameEmptyList() {
        Optional<Animal> animalWithLongestName = Task4.findAnimalWithLongestName(Collections.emptyList());

        // Проверяем, что функция возвращает Optional.empty() для пустого списка
        assertThat(animalWithLongestName).isEmpty();
    }

    @Test
    public void testDominantSexMale() {
        List<Animal> animals = List.of(
            new Animal("Макс", Animal.Type.DOG, Animal.Sex.M, 5, 50, 20, true),
            new Animal("Джек", Animal.Type.DOG, Animal.Sex.M, 7, 55, 23, true),
            new Animal("Бенджи", Animal.Type.DOG, Animal.Sex.M, 8, 58, 24, false),
            new Animal("Мистер Пушистик", Animal.Type.CAT, Animal.Sex.M, 3, 30, 10, false),
            new Animal("Луна", Animal.Type.CAT, Animal.Sex.F, 3, 29, 10, false)
        );
        assertThat(Task5.dominantSex(animals)).isEqualTo(Animal.Sex.M);
    }

    @Test
    public void testDominantSexFemale() {
        List<Animal> animals = List.of(
            new Animal("Луна", Animal.Type.CAT, Animal.Sex.F, 3, 29, 10, false),
            new Animal("Белла", Animal.Type.CAT, Animal.Sex.F, 4, 32, 11, true),
            new Animal("Люси", Animal.Type.DOG, Animal.Sex.F, 6, 60, 25, false),
            new Animal("Молли", Animal.Type.CAT, Animal.Sex.F, 5, 31, 12, true),
            new Animal("Макс", Animal.Type.DOG, Animal.Sex.M, 5, 50, 20, true)
        );
        assertThat(Task5.dominantSex(animals)).isEqualTo(Animal.Sex.F);
    }

    @Test
    public void testDominantSexEqual() {
        List<Animal> animals = List.of(
            new Animal("Макс", Animal.Type.DOG, Animal.Sex.M, 5, 50, 20, true),
            new Animal("Луна", Animal.Type.CAT, Animal.Sex.F, 3, 29, 10, false),
            new Animal("Мистер Пушистик", Animal.Type.CAT, Animal.Sex.M, 3, 30, 10, false),
            new Animal("Белла", Animal.Type.CAT, Animal.Sex.F, 4, 32, 11, true)
        );
        assertThat(Task5.dominantSex(animals)).isEqualTo(Animal.Sex.F);
    }

    @Test
    public void testHeaviestAnimalByType() {
        Map<Animal.Type, Animal> heaviestAnimalsByType = Task6.heaviestAnimalByType(testAnimals);

        for (Animal.Type type : Animal.Type.values()) {
            testAnimals.stream()
                    .filter(a -> a.type() == type)
                    .max(Comparator.comparingInt(Animal::weight)).ifPresent(expectedAnimal -> assertThat(heaviestAnimalsByType).containsEntry(type, expectedAnimal));

        }
    }

    @Test
    public void testHeaviestAnimalByTypeNoExtraTypes() {
        Map<Animal.Type, Animal> heaviestAnimalsByType = Task6.heaviestAnimalByType(testAnimals);

        // Проверяем, что в карте нет записей для типов, которых нет в списке
        assertThat(heaviestAnimalsByType.keySet()).containsExactlyInAnyOrderElementsOf(
            testAnimals.stream().map(Animal::type).collect(Collectors.toSet())
        );
    }

    @Test
    public void testKthOldestAnimal() {
        int k = 3;
        Optional<Animal> kthOldest = Task7.kthOldestAnimal(testAnimals, k);

        Animal expectedAnimal = testAnimals.stream()
            .sorted(Comparator.comparingInt(Animal::age).reversed())
            .skip(k - 1)
            .findFirst()
            .orElse(null);

        assertThat(kthOldest).isPresent().contains(expectedAnimal);
    }

    @Test
    public void testKthOldestAnimalInvalidK() {
        int k = -1;
        Optional<Animal> kthOldest = Task7.kthOldestAnimal(testAnimals, k);
        assertThat(kthOldest).isEmpty();

        k = testAnimals.size() + 1;
        kthOldest = Task7.kthOldestAnimal(testAnimals, k);
        assertThat(kthOldest).isEmpty();
    }

    @Test
    public void testHeaviestAnimalBelowHeight() {
        int k = 50;
        Optional<Animal> heaviestBelowHeight = Task8.heaviestAnimalBelowHeight(testAnimals, k);

        Animal expectedAnimal = testAnimals.stream()
            .filter(animal -> animal.height() < k)
            .max(Comparator.comparingInt(Animal::weight))
            .orElse(null);

        assertThat(heaviestBelowHeight).isPresent().contains(expectedAnimal);
    }

    @Test
    public void testHeaviestAnimalBelowHeightNone() {
        int k = 1;  // проверям, что не животных ниже 1 см
        Optional<Animal> heaviestBelowHeight = Task8.heaviestAnimalBelowHeight(testAnimals, k);
        assertThat(heaviestBelowHeight).isEmpty();
    }

    @Test
    public void testTotalPawsCount() {
        int totalPaws = Task9.totalPawsCount(testAnimals);

        int expectedTotalPaws = testAnimals.stream()
            .mapToInt(Animal::paws)
            .sum();

        assertThat(totalPaws).isEqualTo(expectedTotalPaws);
    }

    @Test
    public void testTotalPawsCountEmptyList() {
        int totalPaws = Task9.totalPawsCount(Collections.emptyList());
        assertThat(totalPaws).isEqualTo(0);
    }

    @Test
    public void testAnimalsWithMismatchedAgeAndPaws() {
        List<Animal> mismatchedAnimals = Task10.animalsWithMismatchedAgeAndPaws(testAnimals);

        List<Animal> expectedMismatchedAnimals = testAnimals.stream()
            .filter(animal -> animal.age() != animal.paws())
            .collect(Collectors.toList());

        assertThat(mismatchedAnimals).containsExactlyInAnyOrderElementsOf(expectedMismatchedAnimals);
    }

    @Test
    public void testAnimalsWithMismatchedAgeAndPawsAllMatch() {
        // Список животных, у которых возраст совпадает с количеством лап
        List<Animal> animals = List.of(
            new Animal("Birdie", Animal.Type.BIRD, Animal.Sex.M, 2, 25, 1, false),
            new Animal("Spiderman", Animal.Type.SPIDER, Animal.Sex.F, 8, 1, 1, true)
        );
        List<Animal> mismatchedAnimals = Task10.animalsWithMismatchedAgeAndPaws(animals);

        assertThat(mismatchedAnimals).isEmpty();
    }

    @Test
    public void testBitingAnimalsOver100cm() {
        List<Animal> bitingAnimals = Task11.bitingAnimalsOver100cm(testAnimals);

        List<Animal> expectedBitingAnimals = testAnimals.stream()
            .filter(animal -> (animal.bites() == null || animal.bites()) && animal.height() > 100)
            .collect(Collectors.toList());

        assertThat(bitingAnimals).containsExactlyInAnyOrderElementsOf(expectedBitingAnimals);
    }

    @Test
    public void testBitingAnimalsOver100cmNone() {
        // Список животных, которые не могут укусить или имеют рост меньше 100 см
        List<Animal> animals = List.of(
            new Animal("Tiny", Animal.Type.BIRD, Animal.Sex.M, 2, 25, 1, false),
            new Animal("Spiderling", Animal.Type.SPIDER, Animal.Sex.F, 1, 1, 1, false)
        );
        List<Animal> bitingAnimals = Task11.bitingAnimalsOver100cm(animals);

        assertThat(bitingAnimals).isEmpty();
    }

    @Test
    public void testAnimalsWeighingMoreThanHeight() {
        long count = Task12.animalsWeighingMoreThanHeight(testAnimals);

        long expectedCount = testAnimals.stream()
            .filter(animal -> animal.weight() > animal.height())
            .count();

        assertThat(count).isEqualTo(expectedCount);
    }

    @Test
    public void testAnimalsWeighingMoreThanHeightNone() {
        // Список животных, у которых вес не превышает рост
        List<Animal> animals = List.of(
            new Animal("Feather", Animal.Type.BIRD, Animal.Sex.M, 2, 25, 20, false),
            new Animal("TinySpider", Animal.Type.SPIDER, Animal.Sex.F, 1, 3, 1, true)
        );
        long count = Task12.animalsWeighingMoreThanHeight(animals);
        assertEquals(0, count);
    }

    @Test
    public void testAnimalsWithNamesMoreThanTwoWords() {
        List<Animal> multiWordNamedAnimals = Task13.animalsWithNamesMoreThanTwoWords(testAnimals);

        List<Animal> expectedAnimals = testAnimals.stream()
            .filter(animal -> animal.name().split("\\s+").length > 2)
            .collect(Collectors.toList());

        assertThat(multiWordNamedAnimals).containsExactlyInAnyOrderElementsOf(expectedAnimals);
    }

    @Test
    public void testAnimalsWithNamesMoreThanTwoWordsNone() {
        // Список животных, имена которых состоят из двух слов или меньше
        List<Animal> animals = List.of(
            new Animal("Big Bird", Animal.Type.BIRD, Animal.Sex.M, 2, 25, 1, false),
            new Animal("Spider", Animal.Type.SPIDER, Animal.Sex.F, 1, 1, 1, true)
        );
        List<Animal> multiWordNamedAnimals = Task13.animalsWithNamesMoreThanTwoWords(animals);

        assertThat(multiWordNamedAnimals).isEmpty();
    }

    @Test
    public void testHasDogTallerThanExists() {
        int k = 50;
        boolean result = Task14.hasDogTallerThan(testAnimals, k);

        boolean expected = testAnimals.stream()
            .filter(animal -> animal.type() == Animal.Type.DOG)
            .anyMatch(dog -> dog.height() > k);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testHasDogTallerThanNone() {
        // Список животных, где нет собак с ростом больше 100 см
        List<Animal> animals = List.of(
            new Animal("Tiny Dog", Animal.Type.DOG, Animal.Sex.M, 2, 25, 10, false),
            new Animal("Spider", Animal.Type.SPIDER, Animal.Sex.F, 1, 1, 1, true)
        );
        int k = 100;
        boolean result = Task14.hasDogTallerThan(animals, k);

        assertThat(result).isFalse();
    }

    @Test
    public void testTotalWeightOfAnimalsByAgeRange() {
        int k = 2;
        int l = 4;
        Map<Animal.Type, Integer> totalWeights = Task15.totalWeightOfAnimalsByAgeRange(testAnimals, k, l);

        Map<Animal.Type, Integer> expectedTotalWeights = testAnimals.stream()
            .filter(animal -> animal.age() >= k && animal.age() <= l)
            .collect(Collectors.groupingBy(
                Animal::type,
                Collectors.summingInt(Animal::weight)
            ));

        assertThat(totalWeights).isEqualTo(expectedTotalWeights);
    }

    @Test
    public void testTotalWeightOfAnimalsByAgeRangeNone() {
        // Список животных, у которых возраст вне диапазона 10-15 лет
        List<Animal> animals = List.of(
            new Animal("Young Dog", Animal.Type.DOG, Animal.Sex.M, 2, 25, 10, false),
            new Animal("Old Spider", Animal.Type.SPIDER, Animal.Sex.F, 8, 1, 1, true)
        );
        int k = 10;
        int l = 15;
        Map<Animal.Type, Integer> totalWeights = Task15.totalWeightOfAnimalsByAgeRange(animals, k, l);

        assertThat(totalWeights).isEmpty();
    }

    @Test
    public void testSortByTypeSexName() {
        List<Animal> sortedAnimals = Task16.sortByTypeSexName(testAnimals);

        List<Animal> expectedSortedAnimals = testAnimals.stream()
            .sorted(Comparator.comparing(Animal::type)
                .thenComparing(Animal::sex)
                .thenComparing(Animal::name))
            .collect(Collectors.toList());

        assertThat(sortedAnimals).containsExactlyElementsOf(expectedSortedAnimals);
    }

    @Test
    public void testSortByTypeSexNameEmptyList() {
        List<Animal> sortedAnimals = Task16.sortByTypeSexName(Collections.emptyList());
        assertThat(sortedAnimals).isEmpty();
    }

    @Test
    public void testDoSpidersBiteMoreThanDogsTrue() {
        List<Animal> animals = List.of(
            new Animal("Biting Spider", Animal.Type.SPIDER, Animal.Sex.M, 2, 1, 1, true),
            new Animal("Biting Dog", Animal.Type.DOG, Animal.Sex.F, 5, 60, 20, true),
            new Animal("Another Biting Spider", Animal.Type.SPIDER, Animal.Sex.M, 1, 1, 1, true)
        );

        boolean result = Task17.doSpidersBiteMoreThanDogs(animals);
        assertThat(result).isTrue();
    }

    @Test
    public void testDoSpidersBiteMoreThanDogsFalse() {
        List<Animal> animals = List.of(
            new Animal("Biting Spider", Animal.Type.SPIDER, Animal.Sex.M, 2, 1, 1, true),
            new Animal("Biting Dog", Animal.Type.DOG, Animal.Sex.F, 5, 60, 20, true),
            new Animal("Another Biting Dog", Animal.Type.DOG, Animal.Sex.M, 4, 55, 18, true)
        );

        boolean result = Task17.doSpidersBiteMoreThanDogs(animals);
        assertThat(result).isFalse();
    }

    @Test
    public void testDoSpidersBiteMoreThanDogsNoData() {
        List<Animal> animals = List.of(
            new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 2, 25, 1, false)
        );

        boolean result = Task17.doSpidersBiteMoreThanDogs(animals);
        assertThat(result).isFalse();
    }

    @Test
    public void testHeaviestFishFromLists() {
        List<Animal> list1 = List.of(
            new Animal("Tiny Fish", Animal.Type.FISH, Animal.Sex.M, 1, 5, 2, false),
            new Animal("Big Fish", Animal.Type.FISH, Animal.Sex.F, 5, 25, 10, true)
        );

        List<Animal> list2 = List.of(
            new Animal("Huge Fish", Animal.Type.FISH, Animal.Sex.M, 7, 45, 15, false)
        );

        Optional<Animal> result = Task18.heaviestFishFromLists(list1, list2);
        assertThat(result).hasValueSatisfying(fish -> assertThat(fish.name()).isEqualTo("Huge Fish"));
    }

    @Test
    public void testHeaviestFishFromListsNoFish() {
        List<Animal> list1 = List.of(
            new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 2, 25, 1, false),
            new Animal("Dog", Animal.Type.DOG, Animal.Sex.F, 5, 60, 20, true)
        );

        List<Animal> list2 = List.of(
            new Animal("Spider", Animal.Type.SPIDER, Animal.Sex.M, 1, 1, 1, true)
        );

        Optional<Animal> result = Task18.heaviestFishFromLists(list1, list2);
        assertThat(result).isEmpty();
    }

    @Test
    public void testFindAnimalsWithErrors() {
        List<Animal> animals = List.of(
            new Animal(null, Animal.Type.FISH, Animal.Sex.M, 1, 5, 2, false), // имя
            new Animal("Big Fish", Animal.Type.FISH, Animal.Sex.F, -5, 25, 10, true), // age
            new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 2, 25, -1, false) // вес
        );

        Map<String, Set<Task19.ValidationError>> errors = Task19.findAnimalsWithErrors(animals);

        assertThat(errors).hasSize(3);
        assertThat(errors.get(null)).contains(Task19.ValidationError.INVALID_NAME);
        assertThat(errors.get("Big Fish")).contains(Task19.ValidationError.NEGATIVE_AGE);
        assertThat(errors.get("Bird")).contains(Task19.ValidationError.NEGATIVE_WEIGHT);
    }

    @Test
    public void testFindAnimalsWithErrorsNoErrors() {
        List<Animal> animals = List.of(
            new Animal("Fish", Animal.Type.FISH, Animal.Sex.M, 1, 5, 2, false),
            new Animal("Dog", Animal.Type.DOG, Animal.Sex.F, 5, 25, 10, true)
        );

        Map<String, Set<Task19.ValidationError>> errors = Task19.findAnimalsWithErrors(animals);
        assertThat(errors).isEmpty();
    }

    @Test
    public void testFindAnimalsWithStringErrors() {
        List<Animal> animals = List.of(
            new Animal(null, Animal.Type.FISH, Animal.Sex.M, 1, 5, 2, false), // имя
            new Animal("Big Fish", Animal.Type.FISH, Animal.Sex.F, -5, 25, 10, true), // age
            new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 2, 25, -1, false) // вес
        );

        Map<String, String> errors = Task20.findAnimalsWithStringErrors(animals);

        assertThat(errors).hasSize(3);
        assertThat(errors.get(null)).isEqualTo("Недопустимое имя");
        assertThat(errors.get("Big Fish")).isEqualTo("Отрицательный возраст");
        assertThat(errors.get("Bird")).isEqualTo("Отрицательный вес");
    }

    @Test
    public void testFindAnimalsWithStringErrorsNoErrors() {
        List<Animal> animals = List.of(
            new Animal("Fish", Animal.Type.FISH, Animal.Sex.M, 1, 5, 2, false),
            new Animal("Dog", Animal.Type.DOG, Animal.Sex.F, 5, 25, 10, true)
        );

        Map<String, String> errors = Task20.findAnimalsWithStringErrors(animals);
        assertThat(errors).isEmpty();
    }

}
