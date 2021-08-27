//,temp,JmsQueueTransactionTest.java,153,207,temp,JmsQueueBrowserTest.java,59,109
//,3
public class xxx {
    public void testReceiveBrowseReceive() throws Exception {
        Message[] outbound = new Message[] {session.createTextMessage("First Message"), session.createTextMessage("Second Message"), session.createTextMessage("Third Message")};

        // lets consume any outstanding messages from previous test runs
        beginTx();
        while (consumer.receive(1000) != null) {
        }
        commitTx();

        beginTx();
        producer.send(outbound[0]);
        producer.send(outbound[1]);
        producer.send(outbound[2]);
        commitTx();

        // Get the first.
        beginTx();
        assertEquals(outbound[0], consumer.receive(1000));
        consumer.close();
        commitTx();
        
        beginTx();
        QueueBrowser browser = session.createBrowser((Queue)destination);
        Enumeration enumeration = browser.getEnumeration();

        // browse the second
        assertTrue("should have received the second message", enumeration.hasMoreElements());
        assertEquals(outbound[1], (Message)enumeration.nextElement());

        // browse the third.
        assertTrue("Should have received the third message", enumeration.hasMoreElements());
        assertEquals(outbound[2], (Message)enumeration.nextElement());

        LOG.info("Check for more...");
        // There should be no more.
        boolean tooMany = false;
        while (enumeration.hasMoreElements()) {
            LOG.info("Got extra message: " + ((TextMessage)enumeration.nextElement()).getText());
            tooMany = true;
        }
        assertFalse(tooMany);
        LOG.info("close browser...");
        browser.close();

        LOG.info("reopen and consume...");
        // Re-open the consumer.
        consumer = resourceProvider.createConsumer(session, destination);
        // Receive the second.
        assertEquals(outbound[1], consumer.receive(1000));
        // Receive the third.
        assertEquals(outbound[2], consumer.receive(1000));
        consumer.close();

        commitTx();
    }

};