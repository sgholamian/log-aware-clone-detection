//,temp,OpenWireConnectionTimeoutTest.java,111,123,temp,HttpPullConsumerTest.java,73,89
//,3
public class xxx {
    @After
    public void tearDown() throws Exception {
        try {
            stopBroker();
        } catch(Exception e) {
            LOG.warn("Error on Broker stop.");
        }

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
        }

        LOG.info("========== Finished test: {} ==========", name.getMethodName());
    }

};