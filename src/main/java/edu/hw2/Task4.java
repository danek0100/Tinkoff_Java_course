package edu.hw2;

/**
 * This class provides utilities to extract calling method information.
 */
public class Task4 {

    /**
     * Retrieves the calling method's class name and method name.
     *
     * @return a {@link CallingInfo} object containing the class name and method name of the calling method.
     */
    public static CallingInfo callingInfo() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        StackTraceElement callingMethod = stackTrace[1];
        return new CallingInfo(callingMethod.getClassName(), callingMethod.getMethodName());
    }

    // Private constructor to prevent instantiation.
    private Task4() {}

    /**
     * This record represents the information about a calling method, including its class name and method name.
     */
    public record CallingInfo(String className, String methodName) {}
}
