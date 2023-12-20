package edu.project5;


import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@State(Scope.Thread)
public class ReflectionBenchmarkTest {

    @Test
    public void benchmark() throws RunnerException {
        Options options = new OptionsBuilder()
            .include(ReflectionBenchmarkTest.class.getSimpleName())
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .mode(Mode.AverageTime)
            .timeUnit(TimeUnit.NANOSECONDS)
            .forks(1)
            .warmupForks(1)
            .warmupIterations(1)
            .warmupTime(TimeValue.seconds(1))
            .measurementIterations(1)
            .measurementTime(TimeValue.seconds(1))
            .build(); // Для теста используются небольшие значения

        new Runner(options).run();
    }

    record Student(String name, String surname) {
    }

    private Student student;
    private Method method;
    private java.lang.invoke.MethodHandle methodHandle;

    @FunctionalInterface
    interface NameGetter {
        String getName(Student student);
    }

    private NameGetter nameGetter;
    private NameGetter lambdaNameGetter;

    @Setup
    public void setup() throws Throwable {
        student = new Student("Danil", "Sobolev");
        method = Student.class.getMethod("name");

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        methodHandle = lookup.findVirtual(Student.class, "name", MethodType.methodType(String.class));

        MethodType getterType = MethodType.methodType(String.class, Student.class);
        CallSite site = LambdaMetafactory.metafactory(
            lookup,
            "getName",
            MethodType.methodType(NameGetter.class),
            getterType,
            methodHandle,
            getterType
        );
        nameGetter = (NameGetter) site.getTarget().invokeExact();
        lambdaNameGetter = ReflectionBenchmarkTest.Student::name;
    }


    @Benchmark
    public void directAccess(Blackhole bh) {
        String name = student.name();
        bh.consume(name);
    }

    @Benchmark
    public void reflection(Blackhole bh) throws InvocationTargetException, IllegalAccessException {
        String name = (String) method.invoke(student);
        bh.consume(name);
    }

    @Benchmark
    public void methodHandleAccess(Blackhole bh) throws Throwable {
        String name = (String) methodHandle.invokeExact(student);
        bh.consume(name);
    }

    @Benchmark
    public void lambdaMetafactoryAccess(Blackhole bh) {
        String name = nameGetter.getName(student);
        bh.consume(name);
    }

    @Benchmark
    public void lambdaExpressionAccess(Blackhole bh) {
        String name = lambdaNameGetter.getName(student);
        bh.consume(name);
    }
}

