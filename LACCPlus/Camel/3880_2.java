//,temp,MockEndpoint.java,506,524,temp,MockEndpoint.java,481,499
//,3
public class xxx {
    public void assertIsNotSatisfied() throws InterruptedException {
        boolean failed = false;
        try {
            assertIsSatisfied();
            // did not throw expected error... fail!
            failed = true;
        } catch (AssertionError e) {
            if (LOG.isDebugEnabled()) {
                // log incl stacktrace
                LOG.debug("Caught expected failure: {}", e.getMessage(), e);
            } else {
                LOG.info("Caught expected failure: {}", e.getMessage());
            }
        }
        if (failed) {
            // fail() throws the AssertionError to indicate the test failed.
            fail("Expected assertion failure but test succeeded!");
        }
    }

};