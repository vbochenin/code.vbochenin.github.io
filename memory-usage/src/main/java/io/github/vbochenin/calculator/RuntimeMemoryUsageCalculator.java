package io.github.vbochenin.calculator;

import io.github.vbochenin.MemoryUsageCalculator;

public class RuntimeMemoryUsageCalculator implements MemoryUsageCalculator {
    @Override
    public long calculateUsedMemory() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }
}
