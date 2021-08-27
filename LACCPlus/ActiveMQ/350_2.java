//,temp,AbstractVirtualDestTest.java,64,77,temp,PooledConnectionSecurityExceptionTest.java,256,277
//,3
public class xxx {
    @Test
    public void testFailedCreateConsumerConnectionStillWorks() throws JMSException {
        Connection connection = pooledConnFact.createConnection("guest", "password");
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(name.getMethodName());

        try {
            session.createConsumer(queue);
            fail("Should fail to create consumer");
        } catch (JMSSecurityException ex) {
            LOG.info("Caught expected security error");
        }

        queue = session.createQueue("GUESTS." + name.getMethodName());

        MessageProducer producer = session.createProducer(queue);
        producer.close();

        connection.close();
    }

};