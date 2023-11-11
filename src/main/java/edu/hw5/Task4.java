package edu.hw5;

/**
 * Utility class providing password validation based on specific criteria.
 */
public class Task4 {

    /**
     * Checks if the provided password string is valid based on the defined criteria.
     *
     * A password is considered valid if it contains at least one of the following special characters:
     * ~, !, @, #, $, %, ^, &, *, |
     *
     * @param password The password string to be validated.
     * @return {@code true} if the password is valid according to the criteria, {@code false} otherwise.
     */
    public static boolean isValidPassword(String password) {
        return password.matches(".*[~!@#$%^&*|].*");
    }

    private Task4() {}
}
