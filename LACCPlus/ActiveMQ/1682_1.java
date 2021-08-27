//,temp,JMSDurableTopicRedeliverTest.java,43,79,temp,JmsTopicRedeliverTest.java,122,151
//,3
public class xxx {
    public void testRedeliverNewSession() throws Exception {
        String text = "TEST: " + System.currentTimeMillis();
        Message sendMessage = session.createTextMessage(text);

        if (verbose) {
            LOG.info("About to send a message: " + sendMessage + " with text: " + text);
        }
        producer.send(producerDestination, sendMessage);

        // receive but don't acknowledge
        Message unackMessage = consumer.receive(1000);
        assertNotNull(unackMessage);
        String unackId = unackMessage.getJMSMessageID();
        assertEquals(((TextMessage)unackMessage).getText(), text);
        assertFalse(unackMessage.getJMSRedelivered());
        assertEquals(unackMessage.getIntProperty("JMSXDeliveryCount"), 1);
        consumeSession.close();
        consumer.close();

        // receive then acknowledge
        consumeSession = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        consumer = createConsumer();
        Message ackMessage = consumer.receive(1000);
        assertNotNull(ackMessage);
        ackMessage.acknowledge();
        String ackId = ackMessage.getJMSMessageID();
        assertEquals(((TextMessage)ackMessage).getText(), text);
        assertTrue(ackMessage.getJMSRedelivered());
        assertEquals(ackMessage.getIntProperty("JMSXDeliveryCount"), 2);
        assertEquals(unackId, ackId);
        consumeSession.close();
        consumer.close();

        consumeSession = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        consumer = createConsumer();
        assertNull(consumer.receive(1000));
    }

};