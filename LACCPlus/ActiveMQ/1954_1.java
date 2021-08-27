//,temp,TempStoreDataCleanupTest.java,180,212,temp,MemoryUsageCleanupTest.java,174,206
//,2
public class xxx {
    private void send10000messages(CountDownLatch latch) {
        ActiveMQConnection activeMQConnection = null;
        try {
            activeMQConnection = createConnection(null);
            Session session = activeMQConnection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(session
                    .createQueue(queueName));
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            activeMQConnection.start();
            for (int i = 0; i < 10000; i++) {
                TextMessage textMessage = session.createTextMessage();
                textMessage.setText(generateBody(1000));
                textMessage.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);
                producer.send(textMessage);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            }
            producer.close();
        } catch (JMSException e) {
            LOG.warn("Got an error while sending the messages", e);
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