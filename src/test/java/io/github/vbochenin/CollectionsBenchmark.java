package io.github.vbochenin;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.runner.RunWith;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@RunWith(BenchmarkRunner.class)
public class CollectionsBenchmark {

    @Benchmark
    public void addAndRemoveToArrayList(Data data) {
        data.arrayList.add(data.element);
        data.arrayList.remove(data.element);
    }

    @Benchmark
    public void addAndRemoveToLinkedList(Data data) {
        data.linkedList.add(data.element);
        data.linkedList.remove(data.element);
    }

    @State(Scope.Benchmark)
    public static class Data {
        private final List<Object> arrayList = new ArrayList<>();
        private final List<Object> linkedList = new LinkedList<>();

        private Object element;

        @Setup(Level.Iteration)
        public void setUp() {
            element = new Object();
        }
    }
}
