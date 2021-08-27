//,temp,ResilienceTimeoutTest.java,59,73,temp,ResilienceTimeoutThreadPoolTest.java,88,102
//,2
public class xxx {
    @Test
    public void testSlowLoop() throws Exception {
        // this calls the slow route and therefore causes a timeout which
        // triggers an exception
        for (int i = 0; i < 10; i++) {
            try {
                log.info(">>> test run " + i + " <<<");
                template.requestBody("direct:start", "slow");
                fail("Should fail due to timeout");
            } catch (Exception e) {
                // expected a timeout
                assertIsInstanceOf(TimeoutException.class, e.getCause());
            }
        }
    }

};