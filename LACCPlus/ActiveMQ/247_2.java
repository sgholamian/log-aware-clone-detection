//,temp,FailoverTimeoutTest.java,79,106,temp,VMTransportWaitForTest.java,98,114
//,3
public class xxx {
    @Test(timeout=90000)
    public void testWaitForNoBrokerInRegistry() throws Exception {

        long startTime = System.currentTimeMillis();

        try {
            ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(new URI(VM_BROKER_URI_SHORT_WAIT_FOR_START));
            cf.createConnection();
            fail("expect broker not exist exception");
        } catch (JMSException expectedOnNoBrokerAndNoCreate) {
        }

        long endTime = System.currentTimeMillis();

        LOG.info("Total wait time was: {}", endTime - startTime);
        assertTrue(endTime - startTime >= SHORT_WAIT_TIME - 100);
    }

};