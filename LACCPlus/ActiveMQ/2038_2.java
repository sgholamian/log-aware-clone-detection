//,temp,JmsPoolTestSupport.java,49,62,temp,JMSClientSimpleAuthTest.java,64,79
//,3
public class xxx {
    @After
    public void stopBroker() throws Exception {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception ex) {}
            connection = null;
        }

        if (brokerService != null) {
            brokerService.stop();
            brokerService = null;
        }

        LOG.info("========== finished: " + getTestName() + " ==========");
    }

};