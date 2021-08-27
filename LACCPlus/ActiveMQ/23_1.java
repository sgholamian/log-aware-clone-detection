//,temp,JMSClientSimpleAuthTest.java,96,105,temp,JMSClientSimpleAuthTest.java,85,94
//,3
public class xxx {
    @Test(timeout = 10000)
    public void testUnknownUser() throws Exception {
        try {
            connection = JMSClientContext.INSTANCE.createConnection(amqpURI, "nosuchuser", "blah");
            connection.start();
            fail("Expected JMSException");
        } catch (JMSSecurityException ex) {
            LOG.debug("Failed to authenticate connection with unknown user ID");
        }
    }

};