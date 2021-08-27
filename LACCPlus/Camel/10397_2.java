//,temp,ThrottlingGroupingTest.java,95,109,temp,ThrottlerTest.java,152,166
//,2
public class xxx {
    private void assertThrottlerTiming(
            final long elapsedTimeMs, final int throttle, final int intervalMs, final int messageCount) {
        // now assert that they have actually been throttled (use +/- 50 as
        // slack)
        long minimum = calculateMinimum(intervalMs, throttle, messageCount) - 50;
        long maximum = calculateMaximum(intervalMs, throttle, messageCount) + 50;
        // add 500 in case running on slow CI boxes
        maximum += 500;
        log.info("Sent {} exchanges in {}ms, with throttle rate of {} per {}ms. Calculated min {}ms and max {}ms", messageCount,
                elapsedTimeMs, throttle, intervalMs, minimum,
                maximum);

        assertTrue(elapsedTimeMs >= minimum, "Should take at least " + minimum + "ms, was: " + elapsedTimeMs);
        assertTrue(elapsedTimeMs <= maximum + TOLERANCE, "Should take at most " + maximum + "ms, was: " + elapsedTimeMs);
    }

};