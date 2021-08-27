//,temp,OptimizeAcknowledgeWithExpiredMsgsTest.java,220,280,temp,OptimizeAcknowledgeWithExpiredMsgsTest.java,167,218
//,3
public class xxx {
    @Test
    public void testOptimizedAckWithExpiredMsgsSync() throws Exception
    {
        ActiveMQConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory(connectionUri + "?jms.optimizeAcknowledge=true&jms.prefetchPolicy.all=100");

        // Create JMS resources
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("TEST.FOO");

        // ***** Consumer code *****
        MessageConsumer consumer = session.createConsumer(destination);

        // ***** Producer Code *****
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
        TextMessage message;

        // Produce msgs that will expire quickly
        for (int i=0; i<45; i++) {
            message = session.createTextMessage(text);
            producer.send(message,1,1,10);
            LOG.trace("Sent message: "+ message.getJMSMessageID() +
                " with expiry 10 msec");
        }
        // Produce msgs that don't expire
        for (int i=0; i<60; i++) {
            message = session.createTextMessage(text);
            producer.send(message,1,1,30000);
            // producer.send(message);
            LOG.trace("Sent message: "+ message.getJMSMessageID() +
                " with expiry 30 sec");
        }
        sleep(200);

        int counter = 1;
        for (; counter <= 60; ++counter) {
            assertNotNull(consumer.receive(2000));
            LOG.info("counter at " + counter);
        }
        LOG.info("Received all expected messages with counter at: " + counter);

        // Cleanup
        producer.close();
        consumer.close();
        session.close();
        connection.close();
    }

};