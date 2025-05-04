package com.elusivemel.paymentservice.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Utility for randomly choosing between two enum values, with a 1/3 vs. 2/3
 * bias.
 */
public final class ApprovalSelector {

    private ApprovalSelector() {
        // no instances
    }

    /**
     * Returns {@code favored} with ~1/3 probability, or {@code other}
     * otherwise.
     *
     * @param <E> any enum type
     * @param favored the value to pick ~1/3 of the time
     * @param other the value to pick ~2/3 of the time
     * @return either {@code favored} or {@code other}
     */
    public static <E extends Enum<E>> E pickOneThird(E favored, E other) {
        // nextInt(3) yields 0,1,2; only 0 (~1/3 chance) returns favored
        return ThreadLocalRandom.current().nextInt(3) == 0
                ? favored
                : other;
    }

    public static <E extends Enum<E>> String pickOneThirdAsString(
            E favored, E other
    ) {
        E pick = pickOneThird(favored, other);
        return pick.name();   // or pick.toString()
    }

}
