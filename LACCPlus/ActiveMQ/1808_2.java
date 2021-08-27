//,temp,MessageGroupTest.java,109,179,temp,MessageGroupTest.java,37,83
//,3
public class xxx {
    public void testGroupedMessagesDeliveredToOnlyOneConsumer() throws Exception {

        ActiveMQDestination destination = new ActiveMQQueue("TEST");

        // Setup a first connection
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer1 = session.createConsumer(destination);
        MessageProducer producer = session.createProducer(destination);

        // Send the messages.
        for (int i = 0; i < 4; i++) {     	
        	TextMessage message = session.createTextMessage("message " + i);
            message.setStringProperty("JMSXGroupID", "TEST-GROUP");
            message.setIntProperty("JMSXGroupSeq", i + 1);
            LOG.info("sending message: " + message);
            producer.send(message);
        }

        // All the messages should have been sent down connection 1.. just get
        // the first 3
        for (int i = 0; i < 3; i++) {
            TextMessage m1 = (TextMessage)consumer1.receive(500);
            assertNotNull("m1 is null for index: " + i, m1);
            assertEquals(m1.getIntProperty("JMSXGroupSeq"), i + 1);
        }
        
        // Setup a second connection
        Connection connection1 = factory.createConnection(userName, password);
        connection1.start();
        Session session2 = connection1.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer2 = session2.createConsumer(destination);

        // Close the first consumer.
        consumer1.close();

        // The last messages should now go the the second consumer.
        for (int i = 0; i < 1; i++) {
            TextMessage m1 = (TextMessage)consumer2.receive(500);
            assertNotNull("m1 is null for index: " + i, m1);
            assertEquals(m1.getIntProperty("JMSXGroupSeq"), 4 + i);
        }

        //assert that there are no other messages left for the consumer 2
        Message m = consumer2.receive(100);
        assertNull("consumer 2 has some messages left", m);
    }	

};