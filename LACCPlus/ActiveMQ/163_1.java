//,temp,JMSClientSimpleAuthTest.java,187,201,temp,JMSClientSimpleAuthTest.java,159,170
//,3
public class xxx {
    @Test(timeout = 30000)
    public void testCreateTemporaryQueueNotAuthorized() throws JMSException {
        connection = JMSClientContext.INSTANCE.createConnection(amqpURI, "user", "userPassword");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        try {
            session.createTemporaryQueue();
        } catch (JMSSecurityException jmsse) {
        } catch (JMSException jmse) {
            LOG.info("Client should have thrown a JMSSecurityException but only threw JMSException");
        }

        // Should not be fatal
        assertNotNull(connection.createSession(false, Session.AUTO_ACKNOWLEDGE));
    }

};