package com.c2h6s.etshtinker.util;

import java.security.SecureRandom;
import java.util.UUID;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;

public class MathUtil {
    public static UUID getUUIDFromString(String str) {
        int hash = str.hashCode();
        SecureRandom random =EtSHrnd();
        random.setSeed(hash);
        long l0 = random.nextLong();
        long l1 = random.nextLong();
        return new UUID(l0, l1);
    }
}
