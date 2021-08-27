//,temp,CompositeQueueTest.java,56,94,temp,MirroredQueueTest.java,42,76
//,3
public class xxx {
    @Test
    public void testVirtualTopicCreation() throws Exception {
        if (connection == null) {
            connection = createConnection();
        }
        connection.start();

        ConsumerBean messageList1 = new ConsumerBean();
        ConsumerBean messageList2 = new ConsumerBean();
        messageList1.setVerbose(true);
        messageList2.setVerbose(true);

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        Destination producerDestination = getProducerDestination();
        Destination destination1 = getConsumer1Dsetination();
        Destination destination2 = getConsumer2Dsetination();
        
        LOG.info("Sending to: " + producerDestination);
        LOG.info("Consuming from: " + destination1 + " and " + destination2);
        
        MessageConsumer c1 = session.createConsumer(destination1, messageSelector1);
        MessageConsumer c2 = session.createConsumer(destination2, messageSelector2);

        c1.setMessageListener(messageList1);
        c2.setMessageListener(messageList2);

        // create topic producer
        MessageProducer producer = session.createProducer(producerDestination);
        assertNotNull(producer);

        for (int i = 0; i < total; i++) {
            producer.send(createMessage(session, i));
        }

        assertMessagesArrived(messageList1, messageList2);
        assertOriginalDestination(messageList1, messageList2);

    }

};