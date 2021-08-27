//,temp,JMSClientSimpleAuthTest.java,96,105,temp,JMSClientSimpleAuthTest.java,85,94
//,3
public class xxx {
    @Test(timeout = 10000)
    public void testNoUserOrPassword() throws Exception {
        try {
            connection = JMSClientContext.INSTANCE.createConnection(amqpURI, "", "");
            connection.start();
            fail("Expected JMSException");
        } catch (JMSSecurityException ex) {
            LOG.debug("Failed to authenticate connection with no user / password.");
        }
    }

};