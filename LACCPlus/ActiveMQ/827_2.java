//,temp,DestinationListenerTest.java,78,91,temp,JMSClientSimpleAuthTest.java,172,185
//,3
public class xxx {
    @Test(timeout = 30000)
    public void testAnonymousProducerNotAuthorized() throws Exception {
        connection = JMSClientContext.INSTANCE.createConnection(amqpURI, "guest", "guestPassword");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("USERS.txQueue");
        MessageProducer producer = session.createProducer(null);

        try {
            producer.send(queue, session.createTextMessage());
            fail("Should not be able to produce here.");
        } catch (JMSSecurityException jmsSE) {
            LOG.info("Caught expected exception");
        }
    }

};