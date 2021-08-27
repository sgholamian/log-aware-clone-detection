//,temp,JmsPoolTestSupport.java,49,62,temp,JMSClientSimpleAuthTest.java,64,79
//,3
public class xxx {
    @After
    public void tearDown() throws Exception {
        if (brokerService != null) {
            try {
                brokerService.stop();
                brokerService.waitUntilStopped();
                brokerService = null;
            } catch (Exception ex) {
                LOG.warn("Suppress error on shutdown: {}", (Object)ex);
            }
        }

        LOG.info("========== tearDown {} ==========", getTestName());
    }

};