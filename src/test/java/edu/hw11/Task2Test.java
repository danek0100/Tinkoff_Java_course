package edu.hw11;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {


    @Test
    public void modifyArithmeticUtils() throws Exception {
        ByteBuddyAgent.install();

        Class<?> dynamicUtils = new ByteBuddy()
            .redefine(ArithmeticUtils.class)
            .method(ElementMatchers.named("sum"))
            .intercept(MethodDelegation.to(MultiplicationHandler.class))
            .make()
            .load(ArithmeticUtils.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent())
            .getLoaded();

        assertThat(dynamicUtils.getDeclaredMethod("sum", int.class, int.class).invoke(null, 10, 10)).isEqualTo(100);
    }

    static class ArithmeticUtils {
        public static int sum(int a, int b) {
            return a + b;
        }
    }

    public static class MultiplicationHandler {
        public static int multiply(int a, int b) {
            return a * b;
        }
    }
}
