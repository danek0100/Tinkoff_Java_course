package edu.hw11;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.dynamic.scaffold.InstrumentedType;
import org.junit.jupiter.api.Test;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static net.bytebuddy.jar.asm.Opcodes.ATHROW;
import static net.bytebuddy.jar.asm.Opcodes.DUP;
import static net.bytebuddy.jar.asm.Opcodes.IFGE;
import static net.bytebuddy.jar.asm.Opcodes.IF_ICMPLE;
import static net.bytebuddy.jar.asm.Opcodes.IINC;
import static net.bytebuddy.jar.asm.Opcodes.ILOAD;
import static net.bytebuddy.jar.asm.Opcodes.INVOKESPECIAL;
import static net.bytebuddy.jar.asm.Opcodes.ISTORE;
import static net.bytebuddy.jar.asm.Opcodes.LADD;
import static net.bytebuddy.jar.asm.Opcodes.LCONST_1;
import static net.bytebuddy.jar.asm.Opcodes.LLOAD;
import static net.bytebuddy.jar.asm.Opcodes.LRETURN;
import static net.bytebuddy.jar.asm.Opcodes.LSTORE;
import static net.bytebuddy.jar.asm.Opcodes.NEW;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {


    @Test
    public void addTenTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
            .subclass(Object.class)
            .name("SimpleAdderClass")
            .defineMethod("addTen", long.class, Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC)
            .withParameters(int.class)
            .intercept(new AddTenMethod())
            .make();

        Class<?> loaded = dynamicType
            .load(Task2Test.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
            .getLoaded();

        Method addTenMethod = loaded.getMethod("addTen", int.class);
        assertThat(addTenMethod.invoke(null, 5)).isEqualTo(15L);
    }

    static class AddTenMethod implements Implementation {
        @Override
        public ByteCodeAppender appender(Target implementationTarget) {
            return (mv, implementationContext, instrumentedMethod) -> {
                mv.visitCode();
                mv.visitVarInsn(ILOAD, 0); // Загрузка параметра 'n'
                mv.visitIntInsn(Opcodes.BIPUSH, 10); // Загрузка константы '10'
                mv.visitInsn(Opcodes.IADD); // Сложение
                mv.visitInsn(Opcodes.I2L); // Преобразование результата в long
                mv.visitInsn(LRETURN); // Возврат значения
                mv.visitMaxs(2, 2); // Настройка максимального размера стека и локальных переменных
                mv.visitEnd();
                return new ByteCodeAppender.Size(2, 2);
            };
        }

        @Override
        public InstrumentedType prepare(InstrumentedType instrumentedType) {
            return instrumentedType;
        }
    }


    @Test
    public void fibonacciTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
            .subclass(Object.class)
            .name("FibonacciCalculator")
            .defineMethod("fib", long.class, Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC)
            .withParameters(int.class)
            .intercept(new FibonacciMethod())
            .make();

        Class<?> loaded = dynamicType
            .load(Task3Test.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
            .getLoaded();

        Method fibMethod = loaded.getMethod("fib", int.class);
        assertThat(fibMethod.invoke(null, 1)).isEqualTo(55L); // Testing Fibonacci of 10
    }


    static class FibonacciMethod implements Implementation {
        /*
        @Override
        public ByteCodeAppender appender(Target implementationTarget) {
            return (mv, implementationContext, instrumentedMethod) -> {
                mv.visitCode();

                Label startLabel = new Label();
                Label recursiveCase = new Label();
                Label endLabel = new Label();

                mv.visitLabel(startLabel);
                mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null); // Frame for start label
                mv.visitVarInsn(Opcodes.ILOAD, 0); // Load n
                mv.visitInsn(Opcodes.ICONST_2);
                mv.visitJumpInsn(Opcodes.IF_ICMPLT, endLabel); // If n < 2, jump to endLabel

                mv.visitLabel(recursiveCase);
                mv.visitVarInsn(Opcodes.ILOAD, 0); // Load n for recursive call
                mv.visitInsn(Opcodes.ICONST_1);
                mv.visitInsn(Opcodes.ISUB); // n - 1
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, implementationTarget.getInstrumentedType().getInternalName(), "fib", "(I)J", false); // Recursive call fib(n - 1)

                mv.visitVarInsn(Opcodes.ILOAD, 0); // Load n for second recursive call
                mv.visitInsn(Opcodes.ICONST_2);
                mv.visitInsn(Opcodes.ISUB); // n - 2
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, implementationTarget.getInstrumentedType().getInternalName(), "fib", "(I)J", false); // Recursive call fib(n - 2)

                mv.visitInsn(Opcodes.LADD); // Add the results of the two recursive calls

                mv.visitFrame(Opcodes.F_APPEND, 1, new Object[] {Opcodes.LONG}, 0, null); // Frame before jumping back
                mv.visitJumpInsn(Opcodes.GOTO, startLabel);

                mv.visitLabel(endLabel);
                mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null); // Frame for end label
                mv.visitInsn(Opcodes.I2L); // Convert int to long for return
                mv.visitInsn(Opcodes.LRETURN);

                mv.visitMaxs(20, 200); // Adjust the maximum stack size and local variables
                mv.visitEnd();

                return new ByteCodeAppender.Size(200, 200);
            };
        }
*/



/*

        @Override
        public ByteCodeAppender appender(Target implementationTarget) {
            return (mv, implementationContext, instrumentedMethod) -> {
                mv.visitCode();

                // Labels for the start, end, and loop
                Label startLabel = new Label();
                Label endLabel = new Label();
                Label loopLabel = new Label();

                mv.visitLabel(startLabel);
                mv.visitVarInsn(Opcodes.ILOAD, 0); // Load n

                // Base cases for n < 2
                mv.visitInsn(Opcodes.ICONST_2);
                //mv.visitFrame(Opcodes.F_FULL, 0, null, 0, null); // Frame for end label
                mv.visitFrame(Opcodes.F_APPEND,1, new Object[] {Opcodes.INTEGER}, 0, null);
                mv.visitJumpInsn(Opcodes.IF_ICMPLT, endLabel); // If n < 2, jump to endLabel

                // Initialize an array to store Fibonacci numbers
                mv.visitVarInsn(Opcodes.ILOAD, 0); // Load n for array size
                mv.visitInsn(Opcodes.I2L); // Convert int to long for array size
                //mv.visitIntInsn(Opcodes.L2I); // Convert long to int for array size
                mv.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_LONG); // Create new array
                int fibArrayVar = 2; // Local variable index for the array
                mv.visitVarInsn(Opcodes.ASTORE, fibArrayVar); // Store the array reference

                // Initialize the base cases
                mv.visitVarInsn(Opcodes.ALOAD, fibArrayVar); // Load array reference
                mv.visitInsn(Opcodes.ICONST_0);
                mv.visitInsn(Opcodes.LCONST_0);
                mv.visitInsn(Opcodes.LASTORE); // fib[0] = 0

                mv.visitVarInsn(Opcodes.ALOAD, fibArrayVar); // Load array reference
                mv.visitInsn(Opcodes.ICONST_1);
                mv.visitInsn(Opcodes.LCONST_1);
                mv.visitInsn(Opcodes.LASTORE); // fib[1] = 1

                // Loop to calculate the Fibonacci numbers
                mv.visitInsn(Opcodes.ICONST_2); // Start from 2
                mv.visitVarInsn(Opcodes.ISTORE, 3); // Loop variable i
                mv.visitLabel(loopLabel);
                mv.visitVarInsn(Opcodes.ILOAD, 3);
                mv.visitVarInsn(Opcodes.ILOAD, 0);
                mv.visitFrame(Opcodes.F_FULL, 0, null, 0, null); // Frame for end label
                mv.visitJumpInsn(Opcodes.IF_ICMPGE, endLabel); // If i >= n, jump to endLabel

                // Calculate fib[i]
                mv.visitVarInsn(Opcodes.ALOAD, fibArrayVar); // Load array reference
                mv.visitVarInsn(Opcodes.ILOAD, 3);
                mv.visitVarInsn(Opcodes.ALOAD, fibArrayVar); // Load array reference
                mv.visitVarInsn(Opcodes.ILOAD, 3);
                mv.visitInsn(Opcodes.ICONST_1);
                mv.visitInsn(Opcodes.ISUB);
                mv.visitInsn(Opcodes.LALOAD); // Load fib[i - 1]
                mv.visitVarInsn(Opcodes.ALOAD, fibArrayVar); // Load array reference
                mv.visitVarInsn(Opcodes.ILOAD, 3);
                mv.visitInsn(Opcodes.ICONST_2);
                mv.visitInsn(Opcodes.ISUB);
                mv.visitInsn(Opcodes.LALOAD); // Load fib[i - 2]
                mv.visitInsn(Opcodes.LADD); // Add fib[i - 1] and fib[i - 2]
                mv.visitInsn(Opcodes.LASTORE); // Store in fib[i]

                // Increment loop counter and loop back
                mv.visitIincInsn(3, 1);
                mv.visitFrame(Opcodes.F_FULL, 0, null, 0, null); // Frame for end label
                mv.visitJumpInsn(Opcodes.GOTO, loopLabel);

                // Return fib[n]
                mv.visitLabel(endLabel);
                mv.visitVarInsn(Opcodes.ALOAD, fibArrayVar); // Load array reference
                mv.visitVarInsn(Opcodes.ILOAD, 0); // Load n
                mv.visitInsn(Opcodes.LALOAD); // Load fib[n]
                mv.visitInsn(Opcodes.LRETURN);

                mv.visitMaxs(6, fibArrayVar + 1); // Adjust the maximum stack size and local variables
                mv.visitEnd();

                return new ByteCodeAppender.Size(6, fibArrayVar + 1);
            };
        }
*/

        @Override
        public ByteCodeAppender appender(Target implementationTarget) {
            return (mv, implementationContext, instrumentedMethod) -> {
                mv.visitCode();

                // Labels for the start, end, and loop
                Label startLabel = new Label();
                Label endLabel = new Label();
                Label loopLabel = new Label();

                mv.visitLabel(startLabel);
                mv.visitVarInsn(Opcodes.ILOAD, 0); // Load n

                // Base cases for n < 2
                mv.visitInsn(Opcodes.ICONST_2);
                mv.visitJumpInsn(Opcodes.IF_ICMPLT, endLabel); // If n < 2, jump to endLabel

                // Adjusted frame for base case check
                mv.visitFrame(Opcodes.F_APPEND,1, new Object[] {Opcodes.INTEGER}, 0, null);

                // Initialize an array to store Fibonacci numbers
                mv.visitVarInsn(Opcodes.ILOAD, 0); // Load n for array size
                mv.visitInsn(Opcodes.I2L); // Convert int to long for array size
                mv.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_LONG); // Create new array
                int fibArrayVar = 1; // Local variable index for the array
                mv.visitVarInsn(Opcodes.ASTORE, fibArrayVar); // Store the array reference

                // Initialize the base cases
                mv.visitVarInsn(Opcodes.ALOAD, fibArrayVar); // Load array reference
                mv.visitInsn(Opcodes.ICONST_0);
                mv.visitInsn(Opcodes.LCONST_0);
                mv.visitInsn(Opcodes.LASTORE); // fib[0] = 0

                mv.visitVarInsn(Opcodes.ALOAD, fibArrayVar); // Load array reference
                mv.visitInsn(Opcodes.ICONST_1);
                mv.visitInsn(Opcodes.LCONST_1);
                mv.visitInsn(Opcodes.LASTORE); // fib[1] = 1

                // Loop to calculate the Fibonacci numbers
                mv.visitInsn(Opcodes.ICONST_2); // Start from 2
                mv.visitVarInsn(Opcodes.ISTORE, 2); // Loop variable i
                mv.visitLabel(loopLabel);

                // Corrected frame at the start of the loop
                mv.visitFrame(Opcodes.F_APPEND,2, new Object[] {fibArrayVar, Opcodes.INTEGER}, 0, null);

                mv.visitVarInsn(Opcodes.ILOAD, 2);
                mv.visitVarInsn(Opcodes.ILOAD, 0);
                mv.visitJumpInsn(Opcodes.IF_ICMPGE, endLabel); // If i >= n, jump to endLabel

                // Calculate fib[i]
                mv.visitVarInsn(Opcodes.ALOAD, fibArrayVar); // Load array reference
                mv.visitVarInsn(Opcodes.ILOAD, 2);
                mv.visitVarInsn(Opcodes.ALOAD, fibArrayVar); // Load array reference
                mv.visitVarInsn(Opcodes.ILOAD, 2);
                mv.visitInsn(Opcodes.ICONST_1);
                mv.visitInsn(Opcodes.ISUB);
                mv.visitInsn(Opcodes.LALOAD); // Load fib[i - 1]
                mv.visitVarInsn(Opcodes.ALOAD, fibArrayVar); // Load array reference
                mv.visitVarInsn(Opcodes.ILOAD, 2);
                mv.visitInsn(Opcodes.ICONST_2);
                mv.visitInsn(Opcodes.ISUB);
                mv.visitInsn(Opcodes.LALOAD); // Load fib[i - 2]
                mv.visitInsn(Opcodes.LADD); // Add fib[i - 1] and fib[i - 2]
                mv.visitInsn(Opcodes.LASTORE); // Store in fib[i]

                // Increment loop counter and loop back
                mv.visitIincInsn(2, 1);
                mv.visitJumpInsn(Opcodes.GOTO, loopLabel);

                // Return fib[n]
                mv.visitLabel(endLabel);
                mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null); // Frame for end label
                mv.visitVarInsn(Opcodes.ALOAD, fibArrayVar); // Load array reference
                mv.visitVarInsn(Opcodes.ILOAD, 0); // Load n
                mv.visitInsn(Opcodes.LALOAD); // Load fib[n]
                mv.visitInsn(Opcodes.LRETURN);

                mv.visitMaxs(100, 100); // Adjust the maximum stack size and local variables
                mv.visitEnd();

                return new ByteCodeAppender.Size(100, 100);
            };
        }

        /*
        @Override
        public ByteCodeAppender appender(Target implementationTarget) {
            return (mv, implementationContext, instrumentedMethod) -> {
            mv.visitVarInsn(ILOAD, 0);
            Label label1 = new Label();
            mv.visitJumpInsn(IFGE, label1);    //"stay" IFGE case //switch to IFLT in reverse maybe?
            Label label2 = new Label();
            mv.visitLabel(label2);
            //var1 local    methodVisitor.visitInsn(LCONST_0);
            mv.visitVarInsn(LSTORE, 1);    //var2 local
            mv.visitInsn(LCONST_1);
            mv.visitVarInsn(LSTORE, 2);
            //counter local    methodVisitor.visitInsn(ICONST_2);
            mv.visitVarInsn(ISTORE, 3);
            Label label3 = new Label();
                mv.visitLabel(label3);
                mv.visitVarInsn(ILOAD, 4);
                mv.visitVarInsn(ILOAD, 1);    //Return label
            Label label4 = new Label();
                mv.visitJumpInsn(IF_ICMPLE, label4);
            //Sum
                mv.visitVarInsn(LLOAD, 2);
                mv.visitVarInsn(LLOAD, 3);
                mv.visitInsn(LADD);
            //Reassign local vars    methodVisitor.visitVarInsn(LLOAD, 3);
                mv.visitVarInsn(LSTORE, 2);
                mv.visitVarInsn(LSTORE, 3);
            //Increment    methodVisitor.visitVarInsn(ILOAD, 4);
                mv.visitInsn(IINC);
                mv.visitVarInsn(ISTORE, 4);
            //loop    methodVisitor.visitJumpInsn(GOTO, label3);
            //return    methodVisitor.visitLabel(label4);
                mv.visitVarInsn(LLOAD, 3);
                mv.visitInsn(LRETURN);
            //"jump" IFGE case
                mv.visitLabel(label1);
                mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                mv.visitTypeInsn(NEW, "java/lang/IllegalArgumentException");
                mv.visitInsn(DUP);
                mv.visitMethodInsn(INVOKESPECIAL, "java/lang/IllegalArgumentException", "<init>", "()V", false);
                mv.visitInsn(ATHROW);
            return new ByteCodeAppender.Size(-1, 10);
        };
        }
*/
        @Override
        public InstrumentedType prepare(InstrumentedType instrumentedType) {
            return instrumentedType;
        }
    }
}
