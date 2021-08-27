//,temp,NetworkedSyncTest.java,259,316,temp,NetworkedSyncTest.java,186,243
//,3
public class xxx {
    @Override
    public void run() {

        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;

        try {
            ActiveMQConnectionFactory amq = new ActiveMQConnectionFactory(
                    NetworkedSyncTest.broker1URL);
            connection = amq.createConnection();

            connection.setExceptionListener(new javax.jms.ExceptionListener() {
                @Override
                public void onException(javax.jms.JMSException e) {
                    e.printStackTrace();
                }
            });

            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic destination = session.createTopic("TEST.FOO");

            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);

            long counter = 0;

            // Create and send message
            for (int i = 0; i < NetworkedSyncTest.MESSAGE_COUNT; i++) {

                String text = "Hello world! From: "
                        + Thread.currentThread().getName() + " : "
                        + this.hashCode() + ":" + counter;
                TextMessage message = session.createTextMessage(text);
                producer.send(message);
                counter++;

                if ((counter % 1000) == 0)
                    LOG.info("sent " + counter + " messages");

            }
        } catch (Exception ex) {
            LOG.error(ex.toString());
            return;
        } finally {
            try {
                if (producer != null)
                    producer.close();
                if (session != null)
                    session.close();
                if (connection != null)
                    connection.close();
            } catch (Exception e) {
                LOG.error("Problem closing down JMS objects: " + e);
            }
        }
    }

};