package by.vision.rlv;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class ReentrantLockValidatorTests {

    @Test
    public void test() {

        final Set<String> set1 = new TreeSet<>(Arrays
                .asList("{{}}", "{}{}"));
        assertEquals(set1,
                ReentrantLockValidator.validate("{}}{}}"));

        final Set<String> set2 = new TreeSet<>(Arrays
                .asList("{x}x", "{xx}", "{}xx"));
        assertEquals(set2,
                ReentrantLockValidator.validate("{}x}x}"));

        final TreeSet<String> set3 = new TreeSet<>(Collections.singletonList("{{}{{}}{}{}}{}"));
        assertEquals(set3,
                ReentrantLockValidator.validate("{{}{{}}{}{}}{}"));

        final Set<String> set4 = new TreeSet<>(Arrays
                .asList("xxxxx{xxxxxxxxxxxx{xxxx}xxxx}xxxx",
                        "xxxxx{xxxxxxxx}xxxx{xxxxxxxx}xxxx",
                        "xxxxx{xxxxxxxx}xxxx{xxxx}xxxxxxxx",
                        "xxxxx{xxxx}xxxxxxxx{xxxxxxxx}xxxx",
                        "xxxxx{xxxx}xxxxxxxx{xxxx}xxxxxxxx"));
        assertEquals(set4,
                ReentrantLockValidator.validate("xxxxx{xxxx}xxxx}xxxx{xxxx}xxxx}xxxx"));

        final Set<String> set5 = new TreeSet<>(Collections.singletonList("a"));
        assertEquals(set5,
                ReentrantLockValidator.validate("a"));

        final Set<String> set6 = new TreeSet<>(Collections.singletonList(""));
        assertEquals(set6,
                ReentrantLockValidator.validate(""));

        final TreeSet<String> emptySet = new TreeSet<>();
        assertEquals(emptySet,
                ReentrantLockValidator.validate("{"));
        assertEquals(emptySet,
                ReentrantLockValidator.validate("}"));
        assertEquals(emptySet,
                ReentrantLockValidator.validate("{{{"));
        assertEquals(emptySet,
                ReentrantLockValidator.validate("a{a{a{a"));
        assertEquals(emptySet,
                ReentrantLockValidator.validate("}}}"));
        assertEquals(emptySet,
                ReentrantLockValidator.validate(null));
    }

}