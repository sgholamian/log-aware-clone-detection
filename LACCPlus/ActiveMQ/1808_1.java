//,temp,MessageGroupTest.java,109,179,temp,MessageGroupTest.java,37,83
//,3
public class xxx {
    public void testClosingMessageGroup() throws Exception {

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
            LOG.info("sending message: " + message);
            producer.send(message);
        }



        // All the messages should have been sent down consumer1.. just get
        // the first 3
        for (int i = 0; i < 3; i++) {
            TextMessage m1 = (TextMessage)consumer1.receive(500);
            assertNotNull("m1 is null for index: " + i, m1);
        }
        
        // Setup a second consumer
        Connection connection1 = factory.createConnection(userName, password);
        connection1.start();
        Session session2 = connection1.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer2 = session2.createConsumer(destination);
        
        //assert that there are no messages for the consumer 2
        Message m = consumer2.receive(100);
        assertNull("consumer 2 has some messages", m);

        // Close the group
    	TextMessage message = session.createTextMessage("message " + 5);
        message.setStringProperty("JMSXGroupID", "TEST-GROUP");
        message.setIntProperty("JMSXGroupSeq", -1);
        LOG.info("sending message: " + message);
        producer.send(message);
        
        //Send some more messages
        for (int i = 0; i < 4; i++) {     	
        	message = session.createTextMessage("message " + i);
            message.setStringProperty("JMSXGroupID", "TEST-GROUP");
            LOG.info("sending message: " + message);
            producer.send(message);
        }
        
        // Receive the fourth message
        TextMessage m1 = (TextMessage)consumer1.receive(500);
        assertNotNull("m1 is null for index: " + 4, m1);
        
        // Receive the closing message
        m1 = (TextMessage)consumer1.receive(500);
        assertNotNull("m1 is null for index: " + 5, m1);        
        
        //assert that there are no messages for the consumer 1
        m = consumer1.receive(100);
        assertNull("consumer 1 has some messages left", m);

        // The messages should now go to the second consumer.
        for (int i = 0; i < 4; i++) {
            m1 = (TextMessage)consumer2.receive(500);
            assertNotNull("m1 is null for index: " + i, m1);
        }

    }

};