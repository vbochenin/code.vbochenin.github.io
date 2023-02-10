package io.github.vbochenin.calculator;

import io.github.vbochenin.MemoryUsageCalculator;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Method;
import java.util.function.LongSupplier;

public class SecretJmxMemoryUsageCalculator implements MemoryUsageCalculator {
    private final LongSupplier allocatedByThread = initAllocatedMemoryProvider();

    private static LongSupplier initAllocatedMemoryProvider() {
        try {
            Class<?> internalIntf = Class.forName("com.sun.management.ThreadMXBean");
            ThreadMXBean bean = ManagementFactory.getThreadMXBean();
            if (!internalIntf.isAssignableFrom(bean.getClass())) { // Attempts to get the interface from PlatformMXBean
                Class<?> pmo = Class.forName("java.lang.management.PlatformManagedObject");
                Method m = ManagementFactory.class.getMethod("getPlatformMXBean", Class.class, pmo);
                bean = (ThreadMXBean) m.invoke(null, internalIntf);
                if (bean == null) {
                    throw new UnsupportedOperationException("No way to access private ThreadMXBean");
                }
            }

            ThreadMXBean allocMxBean = bean;
            Method allocMxBeanGetter = internalIntf.getMethod("getCurrentThreadAllocatedBytes");

            return () -> {
                try {
                    return (long) allocMxBeanGetter.invoke(allocMxBean);
                } catch (Exception e) {
                    return 0;
                }
            };
        } catch (Exception e) {
            return () -> 0;
        }
    }

    @Override
    public long calculateUsedMemory() {
        return allocatedByThread.getAsLong();
    }
}
