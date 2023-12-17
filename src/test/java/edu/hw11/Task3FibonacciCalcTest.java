package edu.hw11;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.lang.reflect.Modifier;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3FibonacciCalcTest {
    private static final long[] FIB_VALUES = {
        0L, 1L, 1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L
    };
    private static final String FIB_CLASS = "FibonacciProcessor";
    private static final String FIB_METHOD = "computeFib";
    private static final String FIB_METHOD_DESC = "(I)J"; // long computeFib(int n)

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 })
    void testFibonacciGenerator(int index) throws Exception {
        var fibProcessor = new ByteBuddy()
            .subclass(Object.class)
            .name(FIB_CLASS)
            .defineMethod(FIB_METHOD, long.class, Modifier.PUBLIC | Modifier.STATIC)
            .withParameters(int.class)
            .intercept(new Implementation.Simple(new FibCodeGenerator()))
            .make()
            .load(getClass().getClassLoader())
            .getLoaded();

        long computedResult = (long) fibProcessor
            .getMethod(FIB_METHOD, int.class)
            .invoke(null, index);

        assertThat(computedResult).isEqualTo(FIB_VALUES[index]);
    }

    private static class FibCodeGenerator implements ByteCodeAppender {
        @Override
        public @NotNull Size apply(MethodVisitor methodVisitor, Implementation.@NotNull Context implContext, @NotNull MethodDescription methodDesc) {
            methodVisitor.visitCode();

            Label fibLabel = new Label();
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 0);
            methodVisitor.visitInsn(Opcodes.ICONST_1);
            methodVisitor.visitJumpInsn(Opcodes.IF_ICMPGT, fibLabel); // if (n <= 1)

            methodVisitor.visitVarInsn(Opcodes.ILOAD, 0);
            methodVisitor.visitInsn(Opcodes.I2L);
            methodVisitor.visitInsn(Opcodes.LRETURN); // return n

            methodVisitor.visitLabel(fibLabel); // if (n > 1)
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);

            methodVisitor.visitVarInsn(Opcodes.ILOAD, 0);
            methodVisitor.visitInsn(Opcodes.ICONST_1);
            methodVisitor.visitInsn(Opcodes.ISUB);
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, FIB_CLASS, FIB_METHOD, FIB_METHOD_DESC, false); // computeFib(n - 1)

            methodVisitor.visitVarInsn(Opcodes.ILOAD, 0);
            methodVisitor.visitInsn(Opcodes.ICONST_2);
            methodVisitor.visitInsn(Opcodes.ISUB);
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, FIB_CLASS, FIB_METHOD, FIB_METHOD_DESC, false); // computeFib(n - 2)

            methodVisitor.visitInsn(Opcodes.LADD); // computeFib(n - 1) + computeFib(n - 2);
            methodVisitor.visitInsn(Opcodes.LRETURN);
            methodVisitor.visitEnd();

            return new ByteCodeAppender.Size(4, 1);
        }
    }
}
