package io.github.vbochenin;

import io.github.vbochenin.calculator.RuntimeMemoryUsageCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SnapshotBuilder {
    private static final Logger log = LoggerFactory.getLogger(SnapshotBuilder.class);

    private final MemoryUsageCalculator calculator = new RuntimeMemoryUsageCalculator();

    public Snapshot buildSnapshot() {
        long usedMemory = calculateUsedMemory();
        try {
            return doBuildSnapshot();
        } finally {
            log.debug("Used memory: " + (calculateUsedMemory() - usedMemory));
        }
    }

    private long calculateUsedMemory() {
        return calculator.calculateUsedMemory();
    }

    private Snapshot doBuildSnapshot() {
        return new Snapshot();
    }

    public static class Snapshot {

    }
}
