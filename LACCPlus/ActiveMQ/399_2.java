//,temp,AMQ5863CompositePublishTest.java,86,110,temp,JmsSendReceiveStressTest.java,124,140
//,3
public class xxx {
    private void consumeMessages(AtomicLong count) throws Exception {
        JmsConnection connection = (JmsConnection) factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, ActiveMQSession.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(getDestinationName());
        MessageConsumer consumer = session.createConsumer(queue);
        long v;
        while ((v = count.decrementAndGet()) > 0) {
            if ((count.get() % 10000) == 0) {
                LOG.info("Received message: {}", NUM_SENDS - count.get());
            }
            assertNotNull("got message " + v, consumer.receive(15000));
        }
        LOG.info("Received message: {}", NUM_SENDS);

        consumer.close();
    }

};