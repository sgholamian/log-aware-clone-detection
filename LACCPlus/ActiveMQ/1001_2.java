//,temp,CompositeQueueTest.java,56,94,temp,MirroredQueueTest.java,42,76
//,3
public class xxx {
    public void testSendingToQueueIsMirrored() throws Exception {
        if (connection == null) {
            connection = createConnection();
        }
        connection.start();

        ConsumerBean messageList = new ConsumerBean();
        messageList.setVerbose(true);

        Destination consumeDestination = createConsumeDestination();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        LOG.info("Consuming from: " + consumeDestination);

        MessageConsumer c1 = session.createConsumer(consumeDestination);
        c1.setMessageListener(messageList);

        // create topic producer
        ActiveMQQueue sendDestination = new ActiveMQQueue(getQueueName());
        LOG.info("Sending to: " + sendDestination);

        MessageProducer producer = session.createProducer(sendDestination);
        assertNotNull(producer);

        int total = 10;
        for (int i = 0; i < total; i++) {
            producer.send(session.createTextMessage("message: " + i));
        }

        ///Thread.sleep(1000000);

        messageList.assertMessagesArrived(total);

        LOG.info("Received: " + messageList);
    }

};