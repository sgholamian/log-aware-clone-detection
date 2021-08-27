//,temp,NetworkedSyncTest.java,259,316,temp,NetworkedSyncTest.java,186,243
//,3
public class xxx {
    @Override
    public void run() {
        Connection connection = null;
        Session session = null;
        MessageConsumer consumer = null;

        try {
            ActiveMQConnectionFactory amq = new ActiveMQConnectionFactory(
                    NetworkedSyncTest.broker2URL);
            connection = amq.createConnection();
            // need to set clientID when using durable subscription.
            connection.setClientID("tmielke");

            connection.setExceptionListener(new javax.jms.ExceptionListener() {
                @Override
                public void onException(javax.jms.JMSException e) {
                    e.printStackTrace();
                }
            });

            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic("TEST.FOO");
            consumer = session.createDurableSubscriber((Topic) destination,"tmielke");

            long counter = 0;
            // Wait for a message
            for (int i = 0; i < NetworkedSyncTest.MESSAGE_COUNT; i++) {
                Message message2 = consumer.receive();
                if (message2 instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message2;
                    textMessage.getText();
                    // logger.info("Received: " + text);
                } else {
                    LOG.error("Received message of unsupported type. Expecting TextMessage. "+ message2);
                }
                counter++;
                if ((counter % 1000) == 0)
                    LOG.info("received " + counter + " messages");


            }
        } catch (Exception e) {
            LOG.error("Error in Consumer: " + e);
            return;
        } finally {
            try {
                if (consumer != null)
                    consumer.close();
                if (session != null)
                    session.close();
                if (connection != null)
                    connection.close();
            } catch (Exception ex) {
                LOG.error("Error closing down JMS objects: " + ex);
            }
        }
    }

};