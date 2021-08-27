//,temp,FaultToleranceTimeoutThreadPoolTest.java,88,103,temp,HystrixTimeoutTest.java,58,71
//,3
public class xxx {
    @Test
    public void testSlowLoop() throws Exception {
        // this calls the slow route and therefore causes a timeout which triggers an exception
        for (int i = 0; i < 10; i++) {
            try {
                LOG.info(">>> test run " + i + " <<<");
                template.requestBody("direct:start", "slow");
                fail("Should fail due to timeout");
            } catch (Exception e) {
                // expected a timeout
                assertIsInstanceOf(TimeoutException.class, e.getCause().getCause());
            }
        }
    }

};