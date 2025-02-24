package io.guthub.vbochenin;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

public class HashmapErrorTest {

    @Test
    public void shouldAddMultipleEntries() throws Exception {
        Map<Integer, String> accumulator = new HashMap<>() {
            
        };
        ExecutorService executor = Executors.newFixedThreadPool(2);
        var barrier = new CountDownLatch(2);
        executor.execute(new InTheSameTime(barrier, new RandomPushTask(accumulator)));
        executor.execute(new InTheSameTime(barrier, new RandomPushTask(accumulator)));

        Thread.currentThread().join(1000_000);

    }

    private static final class RandomPushTask implements Runnable {
        private final Random rnd = new Random();
        private final Map<Integer, String> accumulator;
        private boolean running = false;

        private RandomPushTask(Map<Integer, String> accumulator) {
            this.accumulator = accumulator;
        }

        @Override
        public void run() {
            running = true;
            while (running) {
                accumulator.put(rnd.nextInt(), this.toString());
            }
        }

        public void stop() {
            running = false;
        }
    }

    private static final class InTheSameTime implements Runnable {

        private final CountDownLatch barrier;
        private final Runnable toRun;

        private InTheSameTime(CountDownLatch barrier, Runnable toRun) {
            this.barrier = barrier;
            this.toRun = toRun;
        }

        @Override
        public void run() {
            try {
                barrier.countDown();
                barrier.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();  //set the flag back to true
            }
            toRun.run();
        }
    }

}
