//,temp,OpenWireConnectionTimeoutTest.java,111,123,temp,HttpPullConsumerTest.java,73,89
//,3
public class xxx {
    @After
    public void tearDown() throws Exception {
        if (connection != null) {
            try {
                connection.close();
            } catch (Throwable e) {}
            connection = null;
        }

        stopBroker();

        LOG.info("========== start " + getTestName() + " ==========");
    }

};