//,temp,BadConnectionTest.java,39,46,temp,ActiveMQMessageTest.java,538,547
//,3
public class xxx {
    public void testConnectingToUnavailableServer() throws Exception {
        try {
            transport.asyncRequest(new ActiveMQMessage(), null);
            fail("This should never succeed");
        } catch (IOException e) {
            LOG.info("Caught expected exception: " + e, e);
        }
    }

};