//,temp,TrapMessageInJDBCStoreTest.java,155,191,temp,TempStoreDataCleanupTest.java,214,239
//,3
public class xxx {
    private void receiveAndDiscard100messages(CountDownLatch latch) {
        ActiveMQConnection activeMQConnection = null;
        try {
            activeMQConnection = createConnection(null);
            Session session = activeMQConnection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            MessageConsumer messageConsumer = session.createConsumer(
                    session.createQueue(queueName));
            activeMQConnection.start();
            for (int i = 0; i < 100; i++) {
                messageConsumer.receive();
            }
            messageConsumer.close();
            LOG.info("Created and disconnected");
        } catch (JMSException e) {
            LOG.warn("Got an error while receiving the messages", e);
        } finally {
            if (activeMQConnection != null) {
                try {
                    activeMQConnection.close();
                } catch (JMSException e) {
                }
            }
        }
        latch.countDown();
    }

};