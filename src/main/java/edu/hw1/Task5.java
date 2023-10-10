package edu.hw1;


public class Task5 {

    /**
     * Checks if the number or any of its descendants is a palindrome.
     *
     * @param num The input number.
     * @return True if the number or any of its descendants is a palindrome, false otherwise.
     */
    public static boolean isPalindromeDescendant(int num) {
        if (isPalindrome(num) && num >= 10) {
            return true;
        }

        while (num >= 10) {
            num = createDescendant(num);
            if (num < 10) {
                return false;
            }
            if (isPalindrome(num)) {
                return true;
            }
        }

        return false;
    }


    private static boolean isPalindrome(int num) {
        String str = Integer.toString(num);
        StringBuilder sb = new StringBuilder(str);
        return str.equals(sb.reverse().toString());
    }

    private static int createDescendant(int num) {
        StringBuilder sb = new StringBuilder();
        String str = Integer.toString(num);

        for (int i = 0; i < str.length() - 1; i += 2) {
            int sum = Character.getNumericValue(str.charAt(i)) + Character.getNumericValue(str.charAt(i + 1));
            sb.append(sum);
        }

        return Integer.parseInt(sb.toString());
    }

    private Task5() {}
}
