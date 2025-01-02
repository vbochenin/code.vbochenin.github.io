package io.github.vbochenin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.L_Result;

@State
@JCStressTest
public class Main {
    public static final Random rnd = new Random();
    Map<Integer, String> m = new HashMap<>();

    @Actor
    public void mrFirst(L_Result r) {
        r.r1 = Objects.requireNonNull(m.put(rnd.nextInt(), "Message from Mr. First"));
    }

    @Actor
    public void mrSecond(L_Result r) {
        r.r1 = Objects.requireNonNull(m.put(rnd.nextInt(), "Message from Mr. Second"));
    }
}
