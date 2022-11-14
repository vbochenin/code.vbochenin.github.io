package io.github.vbochenin;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.openjdk.jmh.annotations.Benchmark;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BenchmarkRunner extends Runner {

    private final Class<?> benchmarkClass;
    private final List<Method> benchmarkMethods;

    public BenchmarkRunner(Class<?> benchmarkClass) {
        this.benchmarkClass = benchmarkClass;
        this.benchmarkMethods = Arrays.stream(benchmarkClass.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(Benchmark.class))
                .collect(Collectors.toList());
    }

    @Override
    public Description getDescription() {
        //...
        return null;
    }

    @Override
    public void run(RunNotifier notifier) {
        //...
    }
}
