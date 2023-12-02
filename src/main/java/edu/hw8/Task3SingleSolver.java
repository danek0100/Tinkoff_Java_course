package edu.hw8;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


/**
 * The Task3SingleSolver class is responsible for solving password hashes.
 * It provides functionality to generate potential passwords and match them against a set of given hashes.
 */
public class Task3SingleSolver {

    private static long passwordNum = 0;
    private final char[] availableAlphabet = " abcdefghijklmnopqrstuvwxyz123456789".toCharArray();


    /**
     * Generates the next potential password in the sequence.
     *
     * @return A string representing the next potential password.
     */
    private String nextPassword() {
        long num = passwordNum++;
        StringBuilder password = new StringBuilder();

        do {
            password.append(availableAlphabet[(int) (num % availableAlphabet.length)]);
            num /= availableAlphabet.length;
        } while (num > 0);
        return password.reverse().toString();
    }

    /**
     * Solves the given password hashes by matching them with generated passwords.
     *
     * @param passwordsBase A map of password hashes where keys are hashes and values are usernames.
     * @param maxPasswordLength The maximum length of passwords to consider.
     * @return A map where keys are usernames and values are their corresponding passwords.
     * @throws NoSuchAlgorithmException If the MD5 algorithm is not available.
     */
    public Map<String, String> solve(Map<String, String> passwordsBase, int maxPasswordLength)
        throws NoSuchAlgorithmException {

        Map<String, String> needToDecrypt = passwordsBase;
        Map<String, String> results = new HashMap<>();
        while (!needToDecrypt.isEmpty()) {
            String candidate = nextPassword();

            if (candidate.length() > maxPasswordLength) {
                break;
            }

            byte[] bytesOfMessage = candidate.getBytes(StandardCharsets.UTF_8);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] theMD5digest = md.digest(bytesOfMessage);
            String hash = getHexString(theMD5digest).toLowerCase();

            if (needToDecrypt.containsKey(hash)) {
                results.put(needToDecrypt.get(hash), candidate);
                needToDecrypt.remove(hash);
            }
        }
        return results;
    }

    /**
     * Converts a byte array to a hex string representation.
     *
     * @param bytes The byte array to be converted.
     * @return The hex string representation of the byte array.
     */
    public static String getHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(byteToHexString(b));
        }
        return hexString.toString();
    }

    /**
     * Convert a byte to a hex string
     *
     * @param data array
     * @return String representation in hex
     */
    @SuppressWarnings("MagicNumber")
    public static String byteToHexString(byte data) {

        StringBuffer buf = new StringBuffer();
        buf.append(toHexChar((data >>> 4) & 0x0F));
        buf.append(toHexChar(data & 0x0F));

        return buf.toString();
    }

    /**
     * Convert a integer into a hexadecimal character
     *
     * @param i integer
     * @return hex char
     */
    @SuppressWarnings("MagicNumber")
    public static char toHexChar(int i) {
        if ((0 <= i) && (i <= 9)) {
            return (char) ('0' + i);
        } else {
            return (char) ('a' + (i - 10));
        }
    }
}
