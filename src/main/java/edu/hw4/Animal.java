package edu.hw4;


public record Animal(
    String name,
    Type type,
    Sex sex,
    int age,
    int height,
    int weight,
    Boolean bites
) {
    public enum Type {
        CAT, DOG, BIRD, FISH, SPIDER
    }

    public enum Sex {
        M, F
    }

    public static final int FOUR_PAWS = 4;
    public static final int TWO_PAWS = 2;
    public static final int NO_PAWS = 0;
    public static final int EIGHT_PAWS = 8;

    public int paws() {
        return switch (type) {
            case CAT, DOG -> FOUR_PAWS;
            case BIRD -> TWO_PAWS;
            case FISH -> NO_PAWS;
            case SPIDER -> EIGHT_PAWS;
        };
    }
}
