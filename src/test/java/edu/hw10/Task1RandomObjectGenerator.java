package edu.hw10;

import edu.hw10.annotation.Max;
import edu.hw10.annotation.Min;
import edu.hw10.annotation.NotNull;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class Task1RandomObjectGeneratorTest {

    private final Task1RandomObjectGenerator generator = new Task1RandomObjectGenerator();

    @Test
    void testObjectCreation() throws ReflectiveOperationException {
        var instance = generator.nextObject(MyClass.class);
        var numbers = generator.nextObject(Numbers.class);
        assertThat(instance).isNotNull();
        assertThat(numbers).isNotNull();
    }

    @Test
    void testFactoryMethodCreation() throws ReflectiveOperationException {
        var factoryInstance = generator.nextObject(MyClass.class, "create");
        assertThat(factoryInstance).isNotNull();
    }

    @Test
    void testNotNullAnnotation() throws ReflectiveOperationException {
        var instance = generator.nextObject(MyClass.class);
        assertThat(instance.notNullField).isNotNull();
    }

    @Test
    void testMinAnnotation() throws ReflectiveOperationException {
        var instance = generator.nextObject(MyClass.class);
        assertThat(instance.minField).isGreaterThanOrEqualTo(1);
    }

    @Test
    void testMaxAnnotation() throws ReflectiveOperationException {
        var instance = generator.nextObject(MyClass.class);
        assertThat(instance.maxField).isLessThanOrEqualTo(100);
    }

    static class MyClass {
        String notNullField;
        int minField;
        int maxField;

        public MyClass(@NotNull String notNullField, @Min(1) int minField, @Max(100) int maxField) {
            this.notNullField = notNullField;
            this.minField = minField;
            this.maxField = maxField;
        }

        static public MyClass create() {
            return new MyClass("Test", 1, 100);
        }
    }

    record Numbers(int one, int two, int three) {}
}
