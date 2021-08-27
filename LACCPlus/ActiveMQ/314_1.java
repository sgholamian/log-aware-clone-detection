//,temp,TempStorageConfigBrokerTest.java,67,100,temp,TempStorageBlockedBrokerTest.java,170,217
//,3
public class xxx {
    @Test(timeout=360000)
    @Ignore("blocks in hudson, needs investigation")
    public void testFillTempAndConsumeWithBadTempStoreConfig() throws Exception {

        createBrokerWithInvalidTempStoreConfig();

        broker.getSystemUsage().setSendFailIfNoSpace(true);
        destination = new ActiveMQQueue("Foo");

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerUri);
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
            assertTrue("Should not be able to send 100 messages: ", messagesSent.get() < 100);
            LOG.info("Got resource exception : " + ex + ", after sent: " + messagesSent.get());
        }
    }

};