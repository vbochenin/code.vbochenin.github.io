package io.github.vbochenin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.L_Result;

@State
@JCStressTest
@Outcome(id = "null",  expect = Expect.ACCEPTABLE, desc = "Boolean value is guarded")
public class HashmapTest {
    public static final Random rnd = new Random();
    public static final Map<Integer, String> m = new HashMap<>();

    @Actor
    public void mrFirst(L_Result r) {
        r.r1 = m.put(rnd.nextInt(), "Message from Mr. First");
    }

    @Actor
    public void mrSecond(L_Result r) {
        r.r1 = m.put(rnd.nextInt(), "Message from Mr. Second");
    }
}
