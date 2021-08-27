//,temp,MessageGroupTest.java,85,107,temp,AmqpAndStompInteropTest.java,165,182
//,3
public class xxx {
    public void testAddingConsumer() throws Exception {
        ActiveMQDestination destination = new ActiveMQQueue("TEST");

        // Setup a first connection
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        MessageProducer producer = session.createProducer(destination);
        //MessageConsumer consumer = session.createConsumer(destination);
        
    	TextMessage message = session.createTextMessage("message");
        message.setStringProperty("JMSXGroupID", "TEST-GROUP");
        
        LOG.info("sending message: " + message);
        producer.send(message);
        
        MessageConsumer consumer = session.createConsumer(destination);
        
        TextMessage msg = (TextMessage)consumer.receive();
        assertNotNull(msg);
        boolean first = msg.getBooleanProperty("JMSXGroupFirstForConsumer");
        assertTrue(first);
    }    

};