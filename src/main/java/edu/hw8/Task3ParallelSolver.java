package edu.hw8;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * The Task3ParallelSolver class is designed to solve password hashes in parallel.
 * It utilizes multithreading to efficiently generate potential passwords and match them against a set of given hashes.
 */
public class Task3ParallelSolver {

    private final char[] availableAlphabet = " abcdefghijklmnopqrstuvwxyz123456789".toCharArray();

    /**
     * Generates the next potential password based on the given index.
     *
     * @param index The index to generate the password for.
     * @return A string representing the potential password.
     */
    private String nextPassword(long index) {
        StringBuilder password = new StringBuilder();
        long num = index;
        do {
            password.append(availableAlphabet[(int) (num % availableAlphabet.length)]);
            num /= availableAlphabet.length;
        } while (num > 0);
        return password.reverse().toString();
    }


    /**
     * Solves the given password hashes in parallel by matching them with generated passwords.
     *
     * @param passwordsBase A map of password hashes where keys are hashes and values are usernames.
     * @param maxPasswordLength The maximum length of passwords to consider.
     * @return A map where keys are usernames and values are their corresponding passwords.
     * @throws InterruptedException If the thread execution is interrupted.
     * @throws ExecutionException If an exception occurs during the computation.
     */
    public Map<String, String> solve(Map<String, String> passwordsBase, int maxPasswordLength)
        throws InterruptedException, ExecutionException {

        long maxAvailableIndex = (long) Math.pow(availableAlphabet.length, maxPasswordLength);
        int threadsCount = Runtime.getRuntime().availableProcessors();
        Map<String, String> results = new HashMap<>();
        try (ExecutorService executor = Executors.newFixedThreadPool(threadsCount)) {

            List<Future<Map<String, String>>> futures = new ArrayList<>();

            for (int i = 0; i < threadsCount; i++) {
                final int threadId = i;
                Callable<Map<String, String>> task = () -> {
                    long startIndex = maxAvailableIndex / threadsCount * threadId;
                    long endIndex = threadId == threadsCount - 1 ? maxAvailableIndex
                        : startIndex + maxAvailableIndex / threadsCount;
                    Map<String, String> localResults = new HashMap<>();

                    for (long index = startIndex; index < endIndex; index++) {
                        String candidate = nextPassword(index);

                        if (candidate.length() > maxPasswordLength) {
                            break;
                        }

                        byte[] bytesOfMessage = candidate.getBytes(StandardCharsets.UTF_8);
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        byte[] theMD5digest = md.digest(bytesOfMessage);
                        String hash = getHexString(theMD5digest).toLowerCase();

                        if (passwordsBase.containsKey(hash)) {
                            localResults.put(passwordsBase.get(hash), candidate);
                        }
                    }
                    return localResults;
                };
                futures.add(executor.submit(task));
            }

            for (Future<Map<String, String>> future : futures) {
                results.putAll(future.get());
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
