package edu.hw5;

/**
 * Utility class providing lpr validation.
 */
public class Task5 {
    /**
     * Validates a Russian vehicle number plate.
     *
     * @param numberPlate The number plate to be validated.
     * @return {@code true} if the number plate is valid, {@code false} otherwise.
     */
    public static boolean isValidNumberPlate(String numberPlate) {
        return numberPlate.matches("^[АВЕКМНОРСТУХ]\\d{3}[АВЕКМНОРСТУХ]{2}\\d{2,3}$");
    }

    private Task5() {}
}
