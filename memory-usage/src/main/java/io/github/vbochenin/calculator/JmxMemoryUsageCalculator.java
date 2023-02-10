package io.github.vbochenin.calculator;

import io.github.vbochenin.MemoryUsageCalculator;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

public class JmxMemoryUsageCalculator implements MemoryUsageCalculator {
    private static final MemoryMXBean MEMORY_MX_BEAN = ManagementFactory.getMemoryMXBean();

    @Override
    public long calculateUsedMemory() {
        return MEMORY_MX_BEAN.getHeapMemoryUsage().getUsed();
    }
}
