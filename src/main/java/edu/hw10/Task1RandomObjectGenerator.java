package edu.hw10;

import edu.hw10.annotation.Max;
import edu.hw10.annotation.Min;
import edu.hw10.annotation.NotNull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.RecordComponent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * The `Task1RandomObjectGenerator` class provides a utility for generating random objects of a given class.
 * It can handle regular classes and records, including classes with annotations for custom constraints.
 * This generator can create objects using accessible constructors, factory methods, or default constructors.
 */
 public class Task1RandomObjectGenerator {

    private final static Logger LOGGER = LogManager.getLogger();
    private final Map<Class<?>, List<Constructor<?>>> constructorsCache = new HashMap<>();
    private static final int A = 97;
    private static final int Z = 122;

    /**
     * Generates a random object of the specified class.
     *
     * @param clazz The class of the object to generate.
     * @return A randomly generated object of the specified class.
     * @throws ReflectiveOperationException If there is an issue with reflection or object instantiation.
     */
    public <T> T nextObject(Class<T> clazz) throws ReflectiveOperationException {

        if (clazz.isRecord()) {
            return handleRecordType(clazz);
        }

        List<Constructor<?>> constructors = constructorsCache.computeIfAbsent(clazz, this::findAccessibleConstructors);

        if (constructors.isEmpty()) {
            throw new ReflectiveOperationException("No accessible constructor found for class: " + clazz.getName());
        }

        Constructor<?> randomConstructor = constructors.get(ThreadLocalRandom.current().nextInt(constructors.size()));

        try {
            Class<?>[] paramTypes = randomConstructor.getParameterTypes();
            Object[] paramValues = new Object[paramTypes.length];
            Annotation[][] paramAnnotations = randomConstructor.getParameterAnnotations();

            for (int i = 0; i < paramTypes.length; i++) {
                Class<?> paramType = paramTypes[i];
                NotNull notNull = null;
                Min min = null;
                Max max = null;

                for (Annotation annotation : paramAnnotations[i]) {
                    if (annotation instanceof NotNull) {
                        notNull = (NotNull) annotation;
                    } else if (annotation instanceof Min) {
                        min = (Min) annotation;
                    } else if (annotation instanceof Max) {
                        max = (Max) annotation;
                    }
                }

                paramValues[i] = generateRandomValueWithAnnotations(paramType, notNull, min, max);
            }

            final Object instance;
            if (paramTypes.length > 0) {
                instance = randomConstructor.newInstance(paramValues);
            } else {
                instance = generateRandomObject(clazz);
            }

            if (!clazz.isInstance(instance)) {
                throw new ClassCastException("Created instance is not of type " + clazz.getName());
            }

            return clazz.cast(instance);
        } catch (InstantiationException | IllegalAccessException
                 | InvocationTargetException | UnsupportedOperationException exception) {
            LOGGER.error("Error instantiating class {}: {}", clazz.getName(), exception.getMessage());
            throw new ReflectiveOperationException(exception);
        }
    }

    private <T> T handleRecordType(Class<T> recordClass) throws ReflectiveOperationException {
        RecordComponent[] components = recordClass.getRecordComponents();
        Object[] componentValues = new Object[components.length];

        for (int i = 0; i < components.length; i++) {
            RecordComponent component = components[i];
            Class<?> componentType = component.getType();
            NotNull notNull = component.getAnnotation(NotNull.class);
            Min min = component.getAnnotation(Min.class);
            Max max = component.getAnnotation(Max.class);

            componentValues[i] = generateRandomValueWithAnnotations(componentType, notNull, min, max);
        }

        Constructor<T> canonicalConstructor = recordClass.getDeclaredConstructor(
            Arrays.stream(components).map(RecordComponent::getType).toArray(Class[]::new));

        return canonicalConstructor.newInstance(componentValues);
    }


    private List<Constructor<?>> findAccessibleConstructors(Class<?> clazz) {
        List<Constructor<?>> accessibleConstructors = new ArrayList<>();
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (Modifier.isPublic(constructor.getModifiers()) || Modifier.isProtected(constructor.getModifiers())) {
                accessibleConstructors.add(constructor);
            }
        }
        return accessibleConstructors;
    }

    /**
     * Generates a random object of the specified class with factory method.
     *
     * @param clazz The class of the object to generate.
     * @return A randomly generated object of the specified class.
     * @throws ReflectiveOperationException If there is an issue with reflection or object instantiation.
     */
    public <T> T nextObject(Class<T> clazz, String factoryMethodName) throws ReflectiveOperationException {
        List<Method> factoryMethods = new ArrayList<>();

        for (Method method : clazz.getDeclaredMethods()) {
            int modifiers = method.getModifiers();

            if (Modifier.isStatic(modifiers)
                && method.getName().equals(factoryMethodName)
                && method.getReturnType().isAssignableFrom(clazz)) {
                factoryMethods.add(method);
            }
        }

        if (factoryMethods.isEmpty()) {
            throw new ReflectiveOperationException("No factory method found with name: " + factoryMethodName);
        }

        try {
            Method selectedMethod = factoryMethods.get(ThreadLocalRandom.current().nextInt(factoryMethods.size()));
            selectedMethod.setAccessible(true);

            Class<?>[] paramTypes = selectedMethod.getParameterTypes();
            Object[] paramValues = new Object[paramTypes.length];
            Annotation[][] paramAnnotations = selectedMethod.getParameterAnnotations();

            for (int i = 0; i < paramTypes.length; i++) {
                Class<?> paramType = paramTypes[i];
                NotNull notNull = null;
                Min min = null;
                Max max = null;

                for (Annotation annotation : paramAnnotations[i]) {
                    if (annotation instanceof NotNull) {
                        notNull = (NotNull) annotation;
                    } else if (annotation instanceof Min) {
                        min = (Min) annotation;
                    } else if (annotation instanceof Max) {
                        max = (Max) annotation;
                    }
                }

                paramValues[i] = generateRandomValueWithAnnotations(paramType, notNull, min, max);
            }

            Object result = selectedMethod.invoke(null, paramValues);

            if (!clazz.isInstance(result)) {
                throw new ClassCastException("Factory method "
                    + factoryMethodName + " did not return the correct type");
            }

            return clazz.cast(result);
        } catch (IllegalAccessException | InvocationTargetException | UnsupportedOperationException e) {
            throw new ReflectiveOperationException("Error invoking factory method: " + factoryMethodName, e);
        }
    }

    private  <T> T generateRandomObject(Class<T> clazz) throws ReflectiveOperationException {
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        T instance = constructor.newInstance();

        for (Field field : clazz.getDeclaredFields()) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())
                || java.lang.reflect.Modifier.isFinal(field.getModifiers())) {
                continue;
            }

            field.setAccessible(true);
            Object fieldValue = generateFieldValue(field);
            field.set(instance, fieldValue);
        }
        return instance;
    }

    private Object generateFieldValue(Field field) throws UnsupportedOperationException {
        Class<?> fieldType = field.getType();

        NotNull notNull = field.getAnnotation(NotNull.class);
        Min min = field.getAnnotation(Min.class);
        Max max = field.getAnnotation(Max.class);

        return generateRandomValueWithAnnotations(fieldType, notNull, min, max);
    }


    @SuppressWarnings("MagicNumber")
    private Object generateRandomValueWithAnnotations(Class<?> type, NotNull notNull, Min min, Max max) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        Object value;

        if (Number.class.isAssignableFrom(type) || type.isPrimitive()) {
            value = generateRandomNumberWithinRange(type, min, max, random);
        } else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
            value = random.nextBoolean();
        } else if (type.equals(byte.class) || type.equals(Byte.class)) {
            byte[] bytes = new byte[1];
            random.nextBytes(bytes);
            value = bytes[0];
        } else if (type.equals(char.class) || type.equals(Character.class)) {
            value = (char) (random.nextInt(26) + 'a');
        } else if (type.equals(String.class)) {
            value = generateRandomString(min, max, random);
        } else {
            throw new UnsupportedOperationException("Unsupported type for generation.");
        }

        if (notNull != null) {
            if (value == null) {
                if (type.equals(String.class)) {
                    value = "";
                }
            }
        }

        return value;
    }

    @SuppressWarnings({"ReturnCount", "CyclomaticComplexity"})
    private Object generateRandomNumberWithinRange(Class<?> type, Min min, Max max, ThreadLocalRandom random) {
        if (type.equals(int.class) || type.equals(Integer.class)) {
            return random.nextInt((min != null) ? (int) min.value() : Integer.MIN_VALUE,
                (max != null) ? (int) max.value() + 1 : Integer.MAX_VALUE);
        } else if (type.equals(long.class) || type.equals(Long.class)) {
            return random.nextLong((min != null) ? min.value() : Long.MIN_VALUE,
                (max != null) ? max.value() + 1 : Long.MAX_VALUE);
        } else if (type.equals(double.class) || type.equals(Double.class)) {
            return random.nextDouble((min != null) ? min.value() : Double.MIN_VALUE,
                (max != null) ? max.value() : Double.MAX_VALUE);
        } else if (type.equals(float.class) || type.equals(Float.class)) {
            return random.nextFloat() * ((max != null) ? (float) max.value() - ((min != null) ? (float) min.value()
                : Float.MIN_VALUE) : Float.MAX_VALUE)
                + ((min != null) ? (float) min.value() : Float.MIN_VALUE);
        }
        return null;
    }


    private String generateRandomString(Min min, Max max, ThreadLocalRandom random) {
        int targetStringLength = ThreadLocalRandom.current().nextInt(
            random.nextInt((min != null) ? (int) min.value() : Integer.MIN_VALUE,
            (max != null) ? (int) max.value() + 1 : Integer.MAX_VALUE)
        );

        return random.ints(A, Z + 1)
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }
}
