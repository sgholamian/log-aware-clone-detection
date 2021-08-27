//,temp,CloseRollbackRedeliveryQueueTest.java,81,98,temp,CloseRollbackRedeliveryQueueTest.java,61,79
//,3
public class xxx {
    public void testVerifyConsumerCloseSessionRollbackRedeliveryWithFailoverTransport() throws Throwable {
        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
        MessageConsumer consumer = session.createConsumer(destination);

        Message message = consumer.receive(1000);
        String id = message.getJMSMessageID();
        assertNotNull(message);
        LOG.info("got message " + message);
        consumer.close();
        session.rollback();
        
        consumer = session.createConsumer(destination);
        message = consumer.receive(1000);
        session.commit();
        assertNotNull(message);
        assertEquals("redelivered message", id, message.getJMSMessageID());
        assertEquals(2, message.getLongProperty("JMSXDeliveryCount"));
    }

};