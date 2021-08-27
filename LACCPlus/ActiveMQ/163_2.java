//,temp,JMSClientSimpleAuthTest.java,187,201,temp,JMSClientSimpleAuthTest.java,159,170
//,3
public class xxx {
    @Test(timeout = 30000)
    public void testProducerNotAuthorized() throws Exception {
        connection = JMSClientContext.INSTANCE.createConnection(amqpURI, "guest", "guestPassword");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("USERS.txQueue");
        try {
            session.createProducer(queue);
            fail("Should not be able to produce here.");
        } catch (JMSSecurityException jmsSE) {
            LOG.info("Caught expected exception");
        }
    }

};