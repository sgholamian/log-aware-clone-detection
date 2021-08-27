//,temp,JmsQueueTransactionTest.java,153,207,temp,JmsQueueBrowserTest.java,59,109
//,3
public class xxx {
    public void testReceiveBrowseReceive() throws Exception {
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        ActiveMQQueue destination = new ActiveMQQueue("TEST");
        MessageProducer producer = session.createProducer(destination);
        MessageConsumer consumer = session.createConsumer(destination);
        connection.start();

        Message[] outbound = new Message[]{session.createTextMessage("First Message"),
                                           session.createTextMessage("Second Message"),
                                           session.createTextMessage("Third Message")};

        // lets consume any outstanding messages from previous test runs
        while (consumer.receive(1000) != null) {
        }

        producer.send(outbound[0]);
        producer.send(outbound[1]);
        producer.send(outbound[2]);

        // Get the first.
        assertEquals(outbound[0], consumer.receive(1000));
        consumer.close();

        QueueBrowser browser = session.createBrowser(destination);
        Enumeration<?> enumeration = browser.getEnumeration();

        // browse the second
        assertTrue("should have received the second message", enumeration.hasMoreElements());
        assertEquals(outbound[1], enumeration.nextElement());

        // browse the third.
        assertTrue("Should have received the third message", enumeration.hasMoreElements());
        assertEquals(outbound[2], enumeration.nextElement());

        // There should be no more.
        boolean tooMany = false;
        while (enumeration.hasMoreElements()) {
            LOG.info("Got extra message: " + ((TextMessage) enumeration.nextElement()).getText());
            tooMany = true;
        }
        assertFalse(tooMany);
        browser.close();

        // Re-open the consumer.
        consumer = session.createConsumer(destination);
        // Receive the second.
        assertEquals(outbound[1], consumer.receive(1000));
        // Receive the third.
        assertEquals(outbound[2], consumer.receive(1000));
        consumer.close();
    }

};