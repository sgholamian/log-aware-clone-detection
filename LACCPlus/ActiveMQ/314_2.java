//,temp,TempStorageConfigBrokerTest.java,67,100,temp,TempStorageBlockedBrokerTest.java,170,217
//,3
public class xxx {
    public void testFillTempAndConsume() throws Exception {

        broker.getSystemUsage().setSendFailIfNoSpace(true);
        destination = new ActiveMQQueue("Foo");

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(connectionUri);
        final ActiveMQConnection producerConnection = (ActiveMQConnection) factory.createConnection();
        // so we can easily catch the ResourceAllocationException on send
        producerConnection.setAlwaysSyncSend(true);
        producerConnection.start();

        Session session = producerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        try {
            while (true) {
                Message message = session.createTextMessage(new String(buf) + messagesSent.toString());
                producer.send(message);
                messagesSent.incrementAndGet();
                if (messagesSent.get() % 100 == 0) {
                    LOG.info("Sent Message " + messagesSent.get());
                    LOG.info("Temp Store Usage " + broker.getSystemUsage().getTempUsage().getUsage());
                }
            }
        } catch (ResourceAllocationException ex) {
            LOG.info("Got resource exception : " + ex + ", after sent: " + messagesSent.get());
        }

        // consume all sent
        Connection consumerConnection = factory.createConnection();
        consumerConnection.start();

        Session consumerSession = consumerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = consumerSession.createConsumer(destination);


        while (consumer.receive(messageReceiveTimeout) != null) {
            messagesConsumed.incrementAndGet();
            if (messagesConsumed.get() % 1000 == 0) {
                LOG.info("received Message " + messagesConsumed.get());
                LOG.info("Temp Store Usage " + broker.getSystemUsage().getTempUsage().getUsage());
            }
        }

        assertEquals("Incorrect number of Messages Consumed: " + messagesConsumed.get(), messagesConsumed.get(),
                messagesSent.get());
    }

};