package io.github.vbochenin;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchmarkRunner extends Runner {
    private final Class<?> benchmarkClass;
    private final List<Method> benchmarkMethods;

    public BenchmarkRunner(Class<?> benchmarkClass) {
        this.benchmarkClass = benchmarkClass;
        benchmarkMethods = Arrays.stream(benchmarkClass.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(Benchmark.class))
                .collect(Collectors.toList());
    }

    @Override
    public Description getDescription() {
        Description result = Description.createSuiteDescription(benchmarkClass);
        benchmarkMethods.stream()
                .map(this::getBenchmarkMethodDescription)
                .forEach(result::addChild);
        return result;
    }

    private Description getBenchmarkMethodDescription(Method benchmarkMethod) {
        return Description.createTestDescription(benchmarkClass, benchmarkMethod.getName());
    }

    @Override
    public void run(RunNotifier notifier) {
        for (Method benchmarkMethod : benchmarkMethods) {
            Description testDescription = getBenchmarkMethodDescription(benchmarkMethod);
            try {
            	notifier.fireTestStarted(testDescription);
                Options opt = new OptionsBuilder()
                        .include(".*" + benchmarkClass.getName() + "." + benchmarkMethod.getName() + ".*")
                        .jvmArgsAppend("-Djmh.separateClasspathJAR=true")
                        .result(benchmarkClass.getName() + "." + benchmarkMethod.getName()+"_"+LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + ".json")
                        .resultFormat(ResultFormatType.JSON)
                        .build();

                new org.openjdk.jmh.runner.Runner(opt).run();

                notifier.fireTestFinished(testDescription);
            } catch (Exception e) {
                notifier.fireTestFailure(new Failure(testDescription, e));
                return;
            }
        }
    }
}
